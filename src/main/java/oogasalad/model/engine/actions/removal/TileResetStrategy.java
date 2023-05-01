package oogasalad.model.engine.actions.removal;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

public interface TileResetStrategy {
  void resetTilesOwned(GameHolder gameHolder, List<Player> removedPlayers);

}
