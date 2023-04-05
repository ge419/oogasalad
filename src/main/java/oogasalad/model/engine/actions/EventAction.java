package oogasalad.model.engine.actions;

import oogasalad.model.engine.event_loop.Event;
import oogasalad.model.engine.event_types.EventType;

/**
 * Simple action that just emits an event.
 *
 * @author Dominic Martinez
 */
public class EventAction implements Action {
  Event event;

  public EventAction(EventType type) {
    this(new Event(type));
  }

  public EventAction(Event event) {
    this.event = event;
  }


  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.emitter().emit(event);
  }
}
