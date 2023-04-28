package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;
public record ChooseNumberOfPlayersEvent(Integer numberOfPlayers) implements Event<ChooseNumberOfPlayersEvent> {

  @Override
  public Class<ChooseNumberOfPlayersEvent> eventClass() {
    return ChooseNumberOfPlayersEvent.class;
  }
}
