package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;
public class ChooseNumberOfPlayersEvent implements Event<ChooseNumberOfPlayersEvent> {

  @Override
  public Class<ChooseNumberOfPlayersEvent> eventClass() {
    return ChooseNumberOfPlayersEvent.class;
  }
}
