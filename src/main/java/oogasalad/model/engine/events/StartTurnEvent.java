package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class StartTurnEvent implements Event<StartTurnEvent> {

  @Override
  public Class<StartTurnEvent> eventClass() {
    return StartTurnEvent.class;
  }
}
