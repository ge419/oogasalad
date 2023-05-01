package oogasalad.model.engine.actions.removal;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

/**
 * Concrete implementation of the {@link TileResetStrategy}
 * Used by Actions to remove specific tiles' owners (ex. {@link CheckAndRemovePlayerAction})
 *
 * @Author Jay Yoon
 */
public class RemovedPlayerTileResetStrategy implements TileResetStrategy {

  /**
   * method that removes the owner status of the tiles
   * run after any removal of player
   * <p>
   *   tiles should not be owned by a player that has been removed from the game
   *   resets the ownerStatus and ownerId of the Tiles
   * </p>*
   * @param gameHolder GameHolder to be updated
   * @param removedPlayers list of players that were removed
   **/
  @Override
  public void resetTilesOwned(GameHolder gameHolder, List<Player> removedPlayers) {
    for (Player player : removedPlayers) {
      gameHolder.resetOwners(player.getId());
    }
  }


}
