package oogasalad.model.engine.actions.emits;

import java.util.Objects;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.AttributeEvent;

public record AttributeEventAction(String event) implements Action {

  public AttributeEventAction {
    Objects.requireNonNull(event);
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.emitter().emit(new AttributeEvent(event));
  }
}
