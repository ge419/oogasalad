package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class GameEndEvent implements Event<GameEndEvent> {

  @Override
  public Class<GameEndEvent> eventClass() {
    return GameEndEvent.class;
  }
}
