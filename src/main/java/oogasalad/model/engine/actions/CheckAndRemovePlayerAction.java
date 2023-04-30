package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckAndRemovePlayerAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(CheckAndRemovePlayerAction.class);
  private final GameHolder gameholder;
  private final int scoreMinBound;

  @Inject
  public CheckAndRemovePlayerAction(
      GameHolder holder,
      @Assisted int scoreMinBound
  ) {
    this.gameholder = holder;
    this.scoreMinBound = scoreMinBound;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<Player> playersToRemove = new ArrayList<>();
    for (Player player : gameholder.getPlayers().getList()) {
      if (player.getScore() <= scoreMinBound) {
        LOGGER.info("Player {} Removed from Game Due to Low Score Condition", player.getId());
        playersToRemove.add(player);
        actionParams.emitter().emit(new PlayerRemovalEvent());
      }
    }
    gameholder.removePlayers(playersToRemove);
  }
}
