package oogasalad.model.engine.actions;

import java.util.Objects;
import oogasalad.model.engine.Event;

/**
 * Simple action that just emits an event.
 *
 * @author Dominic Martinez
 */
public record EventAction(Event<?> event) implements Action {

  public EventAction {
    Objects.requireNonNull(event);
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.emitter().emit(event);
  }
}
