package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class DieRolledEvent implements Event<DieRolledEvent> {

  private final int numberRolled;

  public DieRolledEvent(int numberRolled) {
    this.numberRolled = numberRolled;
  }

  public int getNumberRolled() {
    return numberRolled;
  }

  @Override
  public Class<DieRolledEvent> eventClass() {
    return DieRolledEvent.class;
  }
}
