package oogasalad.model.engine.actions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import oogasalad.model.engine.Event;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.emits.EventAction;
import oogasalad.model.engine.prompt.AIPrompter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventActionTest {

  private EventEmitter emitter;
  private Event<?> event;
  private ActionParams actionParams;
  private EventAction action;

  @BeforeEach
  public void setup() {
    emitter = mock(EventEmitter.class);
    event = mock(Event.class);

    actionParams = new ActionParams(emitter, new AIPrompter());
    action = new EventAction(event);
  }

  @Test
  public void testRunAction() {
    action.runAction(actionParams);
    verify(emitter).emit(event);
  }

}
