package oogasalad.model.engine;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Collections;
import java.util.List;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.event_loop.EventHandlerParams;
import oogasalad.model.engine.event_loop.EventRegistrar;
import oogasalad.model.engine.event_loop.MissingActionsException;
import oogasalad.model.engine.event_types.EngineEvent;
import oogasalad.model.engine.event_types.EventType;
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
    // On START_GAME, immediately emit END_GAME
    EventRule startRule = spy(new EventRule(EngineEvent.START_GAME, EngineEvent.END_GAME));

    engine.run(List.of(startRule));

    InOrder inOrder = inOrder(startRule);
    inOrder.verify(startRule).registerEventHandlers(any());
    inOrder.verify(startRule, times(1)).onEvent(any());
  }

  @Test
  void dualRuleGame() {
    // START_GAME -> TEST_EVENT -> END_GAME
    EventRule startRule = spy(new EventRule(EngineEvent.START_GAME, TestEvent.TEST_EVENT_1));
    EventRule endRule = spy(new EventRule(TestEvent.TEST_EVENT_1, EngineEvent.END_GAME));
    // This rule is never called
    EventRule otherRule = spy(new EventRule(TestEvent.TEST_EVENT_2, EngineEvent.START_GAME));

    engine.run(List.of(startRule, endRule, otherRule));

    verify(startRule).registerEventHandlers(any());
    verify(endRule).registerEventHandlers(any());
    verify(otherRule).registerEventHandlers(any());

    verify(otherRule, never()).onEvent(any());

    InOrder inOrder = inOrder(startRule, endRule);
    inOrder.verify(startRule, times(1)).onEvent(any());
    inOrder.verify(endRule, times(1)).onEvent(any());
  }

  @Test
  void ruleOrdering() {
    EventRule startRule = spy(new EventRule(EngineEvent.START_GAME, TestEvent.TEST_EVENT_1));
    EventRule rule1 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_2));
    EventRule rule2 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_3));
    EventRule rule3 = spy(new EventRule(TestEvent.TEST_EVENT_1, TestEvent.TEST_EVENT_4));
    EventRule rule4 = spy(new EventRule(TestEvent.TEST_EVENT_1, EngineEvent.END_GAME));
    List<EventRule> rules = List.of(startRule, rule1, rule2, rule3, rule4);

    engine.run(Collections.unmodifiableList(rules));

    // Rules subscribing to the same event should be triggered in the order they appear in the list
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
    // If END_GAME is not emitted and there are no actions, engine should error
    EventRule startRule = new EventRule(EngineEvent.START_GAME, TestEvent.TEST_EVENT_1);
    List<Rule> rules = List.of(startRule);
    assertThrows(MissingActionsException.class, () -> engine.run(rules));
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

}