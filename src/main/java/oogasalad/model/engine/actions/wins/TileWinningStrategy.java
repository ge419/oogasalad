package oogasalad.model.engine.actions.wins;

import java.util.List;

public class TileWinningStrategy implements WinningConditionStrategy {
  private List<String> winningTileIds;
  private String landedTileId;

  public TileWinningStrategy (String landedTileId, List<String> winningTileIds) {
    this.landedTileId = landedTileId;
    this.winningTileIds = winningTileIds;
  }

  @Override
  public boolean isSatisfied() {
    return winningTileIds.contains(landedTileId);
  }
}
