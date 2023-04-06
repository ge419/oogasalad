package oogasalad.model.engine.event_loop;

import static org.junit.jupiter.api.Assertions.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.TestEvent;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.EventAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleActionQueueTest {

  SimpleActionQueue queue;
  static Injector injector;

  @BeforeAll
  static void initClass() {
    injector = Guice.createInjector(new EngineModule());
  }

  @BeforeEach
  void setUp() {
    queue = injector.getInstance(SimpleActionQueue.class);
  }

  @Test
  void startsEmpty() {
    assertTrue(queue.isEmpty());
    assertTrue(queue.poll().isEmpty());
  }

  @Test
  void addSamePriority() {
    Action action1 = new EventAction(TestEvent.TEST_EVENT_1);
    Action action2 = new EventAction(TestEvent.TEST_EVENT_2);
    Action action3 = new EventAction(TestEvent.TEST_EVENT_3);
    Action action4 = new EventAction(TestEvent.TEST_EVENT_4);

    queue.add(0, action2);
    queue.add(0, action3);
    queue.add(0, action1);
    queue.add(0, action4);

    assertEquals(queue.poll().get(), action2);
    assertEquals(queue.poll().get(), action3);
    assertEquals(queue.poll().get(), action1);
    assertEquals(queue.poll().get(), action4);
    assertTrue(queue.isEmpty());
    assertTrue(queue.poll().isEmpty());
  }

  @Test
  void addMixedPriority() {
    Action action1 = new EventAction(TestEvent.TEST_EVENT_1);
    Action action2 = new EventAction(TestEvent.TEST_EVENT_2);
    Action action3 = new EventAction(TestEvent.TEST_EVENT_3);
    Action action4 = new EventAction(TestEvent.TEST_EVENT_4);

    queue.add(10, action4);
    queue.add(0, action2);
    queue.add(1, action3);
    queue.add(0, action1);
    queue.add(-1, action4);
    queue.add(0, action1);
    queue.add(0, action2);
    queue.add(0, action3);

    assertEquals(queue.poll().get(), action4);
    assertEquals(queue.poll().get(), action2);
    assertEquals(queue.poll().get(), action1);
    assertEquals(queue.poll().get(), action1);
    assertEquals(queue.poll().get(), action2);
    assertEquals(queue.poll().get(), action3);
    assertEquals(queue.poll().get(), action3);
    assertEquals(queue.poll().get(), action4);
    assertTrue(queue.isEmpty());
    assertTrue(queue.poll().isEmpty());
  }
}