package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class DieRolledEvent implements Event<DieRolledEvent> {

  private final int[] numbersRolled;

  public DieRolledEvent(int[] numbersRolled) {
    this.numbersRolled = numbersRolled;
  }

  public int[] getNumbersRolled() {
    return numbersRolled;
  }

  @Override
  public Class<DieRolledEvent> eventClass() {
    return DieRolledEvent.class;
  }
}
