package oogasalad.model.engine.rules;

import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.events.DieRolledEvent;

public class SetDieRule implements Rule {

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(DieRolledEvent.class, this::setDie);
  }

  private void setDie(EventHandlerParams<DieRolledEvent> eventHandlerParams) {
    die.rollDice(eventHandlerParams.event().getNumberRolled());
  }
}