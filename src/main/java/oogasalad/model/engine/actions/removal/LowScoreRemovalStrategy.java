package oogasalad.model.engine.actions.removal;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.events.PlayerCreationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of the {@link PlayerRemovalStrategy} Used by Actions to remove players
 * (ex. {@link CheckAndRemovePlayerAction})
 *
 * @Author Jay Yoon
 */
public class LowScoreRemovalStrategy implements PlayerRemovalStrategy {

  private static final Logger LOGGER = LogManager.getLogger(LowScoreRemovalStrategy.class);

  /**
   * method that removes the list of players that satisfy removal condition updates GameHolder and
   * returns list of removed players
   * <p>
   * checks if player score is lower than given bound if satisfied player is removed from the game
   * </p>*
   *
   * @param gameHolder    GameHolder to be updated
   * @param scoreMinBound minimum score of player to continue playing game
   * @return list of players to be removed
   */
  @Override
  public List<Player> removePlayers(GameHolder gameHolder, double scoreMinBound) {
    List<Player> playersToRemove = new ArrayList<>();
    for (Player player : gameHolder.getPlayers().getList()) {
      if (player.getScore() <= scoreMinBound) {
        LOGGER.info("Player {} removed from game due to low score condition", player.getId());
        playersToRemove.add(player);
      }
    }
    gameHolder.removePlayers(playersToRemove);
    return playersToRemove;
  }

}
