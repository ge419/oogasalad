package oogasalad.model.engine.actions;

import java.util.Objects;
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
