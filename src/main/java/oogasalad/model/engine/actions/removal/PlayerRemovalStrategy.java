package oogasalad.model.engine.actions.removal;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

/**
 * Interface that outlines the strategy to remove players from the game.
 * Updates GameHolder with a smaller size of Players list.
 *
 * @Author Jay Yoon
 */
public interface PlayerRemovalStrategy {
  List<Player> removePlayers(GameHolder gameHolder, double scoreMinBound);
}
