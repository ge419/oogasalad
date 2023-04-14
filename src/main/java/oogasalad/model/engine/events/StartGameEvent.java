package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class StartGameEvent implements Event<StartGameEvent> {

  @Override
  public Class<StartGameEvent> eventClass() {
    return StartGameEvent.class;
  }
}
