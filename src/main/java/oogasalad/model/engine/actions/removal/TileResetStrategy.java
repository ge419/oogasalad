package oogasalad.model.engine.actions.removal;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

/**
 * Interface that outlines the strategy set Tiles unowned. Updates GameHolder Tiles with updated
 * owner attributes
 *
 * @Author Jay Yoon
 */
public interface TileResetStrategy {

  void resetTilesOwned(GameHolder gameHolder, List<Player> removedPlayers);

}
