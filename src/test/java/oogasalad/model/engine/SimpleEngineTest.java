package oogasalad.model.engine;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.EngineEvent;
import oogasalad.model.engine.events.EventType;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class SimpleEngineTest {

  SimpleEngine engine;
  static Injector injector;

  @BeforeAll
  static void initClass() {
    injector = Guice.createInjector(new EngineModule());
  }

  @BeforeEach
  void setUp() {
    engine = injector.getInstance(SimpleEngine.class);
  }

  @Test
  void singleRuleGame() {
    EventRule startRule = spy(new EventRule(EngineEvent.START_GAME, TestEvent.TEST_EVENT_1));

    engine.setRules(List.of(startRule));
    // START_GAME -> TEST_EVENT_1
    engine.runNextAction(mock(Prompter.class));

    InOrder inOrder = inOrder(startRule);
    inOrder.verify(startRule).registerEventHandlers(any());
    inOrder.verify(startRule, times(1)).onEvent(any());
  }

  @Test
  void dualRuleGame() {
    // START_GAME -> TEST_EVENT1 -> TEST_EVENT2
    EventRule startRule = spy(new EventRule(EngineEvent.START_GAME, TestEvent.TEST_EVENT_1));
    EventRule endRule = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    // This rule is never called
    EventRule otherRule = spy(new EventRule(TestEvent.TEST_EVENT_3, EngineEvent.START_GAME));
    Prompter mockPrompter = mock(Prompter.class);

    engine.setRules(List.of(startRule, endRule, otherRule));
    // START_GAME
    engine.runNextAction(mockPrompter);
    // TEST_EVENT_1
    engine.runNextAction(mockPrompter);
    // TEST_EVENT_2
    engine.runNextAction(mockPrompter);
    // No more actions
    assertThrows(MissingActionsException.class, () -> engine.runNextAction(mockPrompter));

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
    DieRule dieRule = new DieRule(EngineEvent.START_GAME);
    Prompter mockPrompter = mock(Prompter.class);

    engine.setRules(List.of(dieRule));
    // START_GAME
    engine.runNextAction(mockPrompter);
    // dice roll
    engine.runNextAction(mockPrompter);

    verify(mockPrompter).rollDice(any());
  }

  @Disabled("Subgame test: disabled until #63 is done")
  @Test
  void subgame() {
    fail();
  }

  @Test
  void throwsOnMissingActions() {
    // If there are no actions and runNextAction is called, engine should error
    EventRule startRule = new EventRule(EngineEvent.START_GAME, TestEvent.TEST_EVENT_1);
    Prompter mockPrompter = mock(Prompter.class);

    engine.setRules(List.of(startRule));
    // START_GAME
    engine.runNextAction(mockPrompter);
    // TEST_EVENT_1
    engine.runNextAction(mockPrompter);
    assertThrows(MissingActionsException.class, () -> engine.runNextAction(mockPrompter));
  }

  /*
   * Helper classes
   */
  private static class EventRule implements Rule {
    EventType listen;
    EventType emit;

    EventRule(EventType listen, EventType emit) {
      this.listen = listen;
      this.emit = emit;
    }

    @Override
    public void registerEventHandlers(EventRegistrar registrar) {
      registrar.registerHandler(listen, this::onEvent);
    }

    public void onEvent(EventHandlerParams params) {
      params.actionQueue().add(1, new EventAction(emit));
    }
  }

  private static class DieRule implements Rule {
    EventType listen;

    DieRule(EventType listen) {
      this.listen = listen;
    }

    @Override
    public void registerEventHandlers(EventRegistrar registrar) {
      registrar.registerHandler(listen, this::onEvent);
    }

    public void onEvent(EventHandlerParams params) {
      params.actionQueue().add(0, new DieAction());
    }

    private static class DieAction implements Action {

      @Override
      public void runAction(ActionParams actionParams) {
        actionParams.prompter().rollDice(() -> {});
      }
    }
  }

}