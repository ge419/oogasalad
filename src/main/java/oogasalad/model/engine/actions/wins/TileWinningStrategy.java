package oogasalad.model.engine.actions.wins;

import java.util.List;
import oogasalad.model.engine.actions.removal.CheckAndRemovePlayerAction;
import oogasalad.model.engine.actions.removal.PlayerRemovalStrategy;

/**
 * Concrete implementation of the {@link WinningConditionStrategy}
 *
 * Used by Actions to check whether player landed on the winning tile (ex. {@link CheckWinAndEndAction})
 * Used by games whose board is finite
 *
 * @Author Jay Yoon
 */
public class TileWinningStrategy implements WinningConditionStrategy {
  private List<String> winningTileIds;
  private String landedTileId;

  public TileWinningStrategy (String landedTileId, List<String> winningTileIds) {
    this.landedTileId = landedTileId;
    this.winningTileIds = winningTileIds;
  }

  /**
   * method that checks whether given tile is one of the winning tiles
   * <p>
   *   if the landed tile is contained in the list of winning tiles,
   *   game end condition is satisfied
   * </p>
   *
   * @return boolean that represents whether winning condition is satisfied
   */
  @Override
  public boolean isSatisfied() {
    return winningTileIds.contains(landedTileId);
  }
}
