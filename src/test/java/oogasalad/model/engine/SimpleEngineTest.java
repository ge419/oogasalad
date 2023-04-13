package oogasalad.model.engine;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.AttributeEvent;
import oogasalad.model.engine.events.StartGameEvent;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class SimpleEngineTest {

  Injector injector;
  SimpleEngine engine;
  Prompter mockPrompter;

  @BeforeEach
  void setUp() {
    mockPrompter = mock(Prompter.class);
    injector = Guice.createInjector(
        new EngineModule(),
        binder -> binder.bind(Prompter.class).toInstance(mockPrompter)
    );
    engine = injector.getInstance(SimpleEngine.class);
  }

  @Test
  void singleRuleGame() {
    EventRule startRule = spy(
        new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_1));

    engine.setRules(List.of(startRule));
    // START_GAME -> TEST_EVENT_1
    engine.runNextAction();

    InOrder inOrder = inOrder(startRule);
    inOrder.verify(startRule).registerEventHandlers(any());
    inOrder.verify(startRule, times(1)).onEvent(any());
  }

  @Test
  void dualRuleGame() {
    // START_GAME -> TEST_EVENT1 -> TEST_EVENT2
    EventRule startRule = spy(
        new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_1));
    EventRule endRule = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    // This rule is never called
    EventRule otherRule = spy(new EventRule(TestEvent.TEST_EVENT_3, TestEvent.TEST_EVENT_3));

    engine.setRules(List.of(startRule, endRule, otherRule));
    // START_GAME
    engine.runNextAction();
    // TEST_EVENT_1
    engine.runNextAction();
    // TEST_EVENT_2
    engine.runNextAction();
    // No more actions
    assertThrows(MissingActionsException.class, () -> engine.runNextAction());

    verify(startRule).registerEventHandlers(any());
    verify(endRule).registerEventHandlers(any());
    verify(otherRule).registerEventHandlers(any());

    verify(otherRule, never()).onEvent(any());

    InOrder inOrder = inOrder(startRule, endRule);
    inOrder.verify(startRule, times(1)).onEvent(any());
    inOrder.verify(endRule, times(1)).onEvent(any());
  }

  @Test
  void promptTest() {
    DieRule<StartGameEvent> dieRule = new DieRule<>(StartGameEvent.class);

    engine.setRules(List.of(dieRule));
    // START_GAME
    engine.runNextAction();
    // dice roll
    engine.runNextAction();

    verify(mockPrompter).rollDice(any());
  }

  @Test
  void orderTest() {
    EventRule rule1 = spy(new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_1));
    EventRule rule2 = spy(new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_2));
    EventRule rule3 = spy(new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_2));
    EventRule rule4 = spy(new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_2));
    EventRule rule5 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    EventRule rule6 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    EventRule rule7 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    EventRule rule8 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    List<EventRule> rules = List.of(
        rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8
    );

    engine.setRules(rules);
    for (int i = 0; i < rules.size() + 1; i++) {
      engine.runNextAction();
    }

    InOrder inOrder = inOrder(rules.toArray());
    for (EventRule rule : rules) {
      inOrder.verify(rule, times(1)).onEvent(any());
    }
  }

  @Disabled("Subgame test: disabled until #63 is done")
  @Test
  void subgame() {
    fail();
  }

  @Test
  void throwsOnMissingActions() {
    // If there are no actions and runNextAction is called, engine should error
    EventRule startRule = new EventRule(className(StartGameEvent.class), TestEvent.TEST_EVENT_1);

    engine.setRules(List.of(startRule));
    // START_GAME
    engine.runNextAction();
    // TEST_EVENT_1
    engine.runNextAction();
    assertThrows(MissingActionsException.class, () -> engine.runNextAction());
  }

  private String className(Class<?> clazz) {
    return clazz.getName();
  }

  /*
   * Helper classes
   */
  private static class EventRule implements Rule {

    String listen;
    String emit;

    EventRule(String listen, String emit) {
      this.listen = listen;
      this.emit = emit;
    }

    @Override
    public void registerEventHandlers(EventRegistrar registrar) {
      registrar.registerHandler(listen, this::onEvent);
    }

    public void onEvent(EventHandlerParams<AttributeEvent> params) {
      params.actionQueue().add(1, new EventAction(new AttributeEvent(emit)));
    }
  }

  private static class DieRule<T extends Event<T>> implements Rule {

    Class<T> listen;

    DieRule(Class<T> listen) {
      this.listen = listen;
    }

    @Override
    public void registerEventHandlers(EventRegistrar registrar) {
      registrar.registerHandler(listen, this::onEvent);
    }

    public void onEvent(EventHandlerParams<T> params) {
      params.actionQueue().add(0, new DieAction());
    }

    private static class DieAction implements Action {

      @Override
      public void runAction(ActionParams actionParams) {
        actionParams.prompter().rollDice(() -> {
        });
      }
    }
  }

}