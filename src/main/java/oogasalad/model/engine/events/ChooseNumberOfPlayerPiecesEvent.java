package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;
public record ChooseNumberOfPlayerPiecesEvent(Integer numberOfPieces) implements Event<ChooseNumberOfPlayerPiecesEvent> {

  @Override
  public Class<ChooseNumberOfPlayerPiecesEvent> eventClass() {
    return ChooseNumberOfPlayerPiecesEvent.class;
  }
}
