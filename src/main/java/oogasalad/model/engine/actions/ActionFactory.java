package oogasalad.model.engine.actions;

import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;

public interface ActionFactory {

  SetCurrentPlayerAction makeSetCurrentPlayerAction(Player player);

  RollDieAction makeRollDieAction(int[] dieResults);

  MoveAction makeMoveAction(Piece piece, List<Tile> moveSequence);
}
