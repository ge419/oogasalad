package oogasalad.model.engine.actions.removal;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LowScoreRemovalStrategy implements PlayerRemovalStrategy {
  private static final Logger LOGGER = LogManager.getLogger(LowScoreRemovalStrategy.class);

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
