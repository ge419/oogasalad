package oogasalad.model.engine.actions.removal;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

public class RemovedPlayerTileResetStrategy implements TileResetStrategy {

  @Override
  public void resetTilesOwned(GameHolder gameHolder, List<Player> removedPlayers) {
    for (Player player : removedPlayers) {
      gameHolder.resetOwners(player.getId());
    }
  }


}
