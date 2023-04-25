package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.events.MoveEvent;

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
    actionParams.emitter().emit(new MoveEvent(moveList));
    piece.setTileId(moveList.get(moveList.size() - 1).getId());
  }
}
