package oogasalad.model.engine.events;

import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Event;

public record TileLandedEvent(Piece piece, Tile landedTile) implements Event<TileLandedEvent> {

  @Override
  public Class<TileLandedEvent> eventClass() {
    return TileLandedEvent.class;
  }
}
