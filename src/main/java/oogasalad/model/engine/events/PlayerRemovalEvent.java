package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class PlayerRemovalEvent implements Event<PlayerRemovalEvent> {

  @Override
  public Class<PlayerRemovalEvent> eventClass() {
    return PlayerRemovalEvent.class;
  }
}
