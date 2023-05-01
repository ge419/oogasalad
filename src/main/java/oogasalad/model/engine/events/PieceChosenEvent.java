package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public record PieceChosenEvent(Integer numberOfPieces) implements Event<PieceChosenEvent> {

  @Override
  public Class<PieceChosenEvent> eventClass() {
    return PieceChosenEvent.class;
  }
}
