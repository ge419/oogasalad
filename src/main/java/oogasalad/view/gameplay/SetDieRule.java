package oogasalad.view.gameplay;

import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.events.DieRolledEvent;
import oogasalad.model.engine.rules.Rule;

public class SetDieRule implements Rule {

  private final Die die;

  public SetDieRule(Die die) {
    this.die = die;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(DieRolledEvent.class, this::setDie);
  }

  private void setDie(EventHandlerParams<DieRolledEvent> eventHandlerParams) {
    // TODO: Multiple dice
    die.rollDice(eventHandlerParams.event().getNumbersRolled()[0]);
  }
}
