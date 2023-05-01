package oogasalad.model.engine.actions.emits;

import java.util.Objects;
import oogasalad.model.engine.Event;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;

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
