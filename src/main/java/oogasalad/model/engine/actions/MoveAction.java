package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.events.MoveEvent;
import oogasalad.model.engine.events.TileLandedEvent;

public class MoveAction implements Action {

  private final Piece piece;
  private final List<Tile> moveList;

  @Inject
  public MoveAction(
      @Assisted Piece piece,
      @Assisted List<Tile> moveList
  ) {
    this.piece = piece;
    this.moveList = moveList;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    Tile landedTile = moveList.get(moveList.size() - 1);
    actionParams.emitter().emit(new MoveEvent(moveList));
    actionParams.emitter().emit(new TileLandedEvent(piece, landedTile));
    piece.setTileId(landedTile.getId());
  }
}
