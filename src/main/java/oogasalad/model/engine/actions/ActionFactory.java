package oogasalad.model.engine.actions;

import com.google.inject.assistedinject.Assisted;
import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;

public interface ActionFactory {

  SetCurrentPlayerAction makeSetCurrentPlayerAction(Player player);

  RollDieAction makeRollDieAction(int[] dieResults);

  MoveAction makeMoveAction(Piece piece, List<Tile> moveSequence);
  CreatePlayersAction makeCreatePlayersAction(
      @Assisted("min") int min,
      @Assisted("max") int max);
  CreatePlayerPieceAction makeCreatePlayerPieceAction();
  CheckAndRemovePlayerAction makeCheckAndRemovePlayerAction(int scoreMinBound);
  CheckWinStateAction makeCheckWinStateAction();
}
