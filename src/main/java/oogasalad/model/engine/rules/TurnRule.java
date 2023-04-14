package oogasalad.model.engine.rules;

import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.StartGameEvent;
import oogasalad.model.engine.events.StartTurnEvent;

public class TurnRule implements Rule {

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartGameEvent.class, this::newTurn);
    registrar.registerHandler(StartTurnEvent.class, this::newTurn);
  }

  private void newTurn(EventHandlerParams<?> eventHandlerParams) {
    eventHandlerParams.actionQueue().add(10, new EventAction(new StartTurnEvent()));
  }
}
