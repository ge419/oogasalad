package oogasalad.model.engine.actions;

import com.google.inject.assistedinject.Assisted;
import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.actions.creation.CreatePlayerPieceAction;
import oogasalad.model.engine.actions.creation.CreatePlayersAction;
import oogasalad.model.engine.actions.moves.MoveAction;
import oogasalad.model.engine.actions.moves.RollDieAction;
import oogasalad.model.engine.actions.removal.CheckAndRemovePlayerAction;
import oogasalad.model.engine.actions.removal.PlayerRemovalStrategy;
import oogasalad.model.engine.actions.removal.TileResetStrategy;
import oogasalad.model.engine.actions.scores.AlterPlayerScoreAction;
import oogasalad.model.engine.actions.turns.SetCurrentPlayerAction;
import oogasalad.model.engine.actions.wins.CheckWinAndEndAction;
import oogasalad.model.engine.actions.wins.WinningConditionStrategy;

/**
 * The primary abstraction for making Engine {@link Action} by Rules
 *
 * @author Dominic Martinez, Jay Yoon
 */
public interface ActionFactory {

  SetCurrentPlayerAction makeSetCurrentPlayerAction(Player player);
  RollDieAction makeRollDieAction(int[] dieResults);
  MoveAction makeMoveAction(Piece piece, List<Tile> moveSequence);
  CreatePlayersAction makeCreatePlayersAction(
      @Assisted("min") int min,
      @Assisted("max") int max,
      @Assisted("piecePerPlayer") int piecePerPlayer)
      ;
  CreatePlayerPieceAction makeCreatePlayerPieceAction();
  CheckAndRemovePlayerAction makeCheckAndRemovePlayerAction(int scoreMinBound, PlayerRemovalStrategy playerRemovalStrategy, TileResetStrategy tileResetStrategy);
  CheckWinAndEndAction makeCheckWinStateAction(WinningConditionStrategy strategy);
  AlterPlayerScoreAction makeAlterPlayerScoreAction(Player player, double delta);
}
