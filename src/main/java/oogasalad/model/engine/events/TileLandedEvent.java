package oogasalad.model.engine.events;

import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Event;

public class TileLandedEvent implements Event<TileLandedEvent> {

  private final Tile landedTile;

  public TileLandedEvent(Tile landedTile) {
    this.landedTile = landedTile;
  }

  @Override
  public Class<TileLandedEvent> eventClass() {
    return TileLandedEvent.class;
  }

  public Tile getLandedTile() {
    return landedTile;
  }
}
