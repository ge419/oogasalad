package oogasalad.model.engine.events;

import java.util.Collections;
import java.util.List;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Event;

public class MoveEvent implements Event<MoveEvent> {

  private final List<Tile> moveSequence;

  public MoveEvent(List<Tile> moveSequence) {
    this.moveSequence = Collections.unmodifiableList(moveSequence);
  }

  @Override
  public Class<MoveEvent> eventClass() {
    return MoveEvent.class;
  }

  public List<Tile> getMoveSequence() {
    return moveSequence;
  }
}
