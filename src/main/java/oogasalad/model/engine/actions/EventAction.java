package oogasalad.model.engine.actions;

import java.util.Map;
import java.util.Objects;
import oogasalad.model.engine.Event;
import oogasalad.model.engine.events.EventType;

/**
 * Simple action that just emits an event.
 *
 * @author Dominic Martinez
 */
public record EventAction(Event event) implements Action {

  public EventAction(EventType type) {
    this(new Event(type, Map.of()));
  }

  public EventAction {
    Objects.requireNonNull(event);
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.emitter().emit(event);
  }
}
