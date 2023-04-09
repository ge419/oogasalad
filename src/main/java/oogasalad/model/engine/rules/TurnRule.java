package oogasalad.model.engine.rules;

import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.EngineEvent;
import oogasalad.model.engine.events.TurnEvent;

public class TurnRule implements Rule {

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(EngineEvent.START_GAME, this::newTurn);
    registrar.registerHandler(TurnEvent.START_TURN, this::newTurn);
  }

  private void newTurn(EventHandlerParams eventHandlerParams) {
    eventHandlerParams.actionQueue().add(10, new EventAction(TurnEvent.START_TURN));
  }
}
