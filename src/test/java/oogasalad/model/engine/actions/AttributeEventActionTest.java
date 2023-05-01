package oogasalad.model.engine.actions;

import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.emits.AttributeEventAction;
import oogasalad.model.engine.events.AttributeEvent;
import oogasalad.model.engine.prompt.AIPrompter;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AttributeEventActionTest {

  public static final String TEST_EVENT = "testEvent";

  @Test(expected = NullPointerException.class)
  public void testConstructor_NullEvent_ThrowsNullPointerException() {
    new AttributeEventAction(null);
  }

  @Test
  public void testRunAction() {
    AttributeEventAction action = new AttributeEventAction(TEST_EVENT);
    EventEmitter eventEmitter = mock(EventEmitter.class);
    ActionParams actionParams = new ActionParams(eventEmitter, new AIPrompter());

    action.runAction(actionParams);

    ArgumentCaptor<AttributeEvent> eventCaptor = ArgumentCaptor.forClass(AttributeEvent.class);
    verify(eventEmitter).emit(eventCaptor.capture());
    AttributeEvent emittedEvent = eventCaptor.getValue();

    assertEquals(AttributeEvent.class, emittedEvent.eventClass());
  }


}
