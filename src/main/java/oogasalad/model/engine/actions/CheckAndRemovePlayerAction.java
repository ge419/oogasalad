package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

public class CheckAndRemovePlayerAction implements Action {

  private final GameHolder gameholder;
  private final int minScore = 0;

  @Inject
  public CheckAndRemovePlayerAction(
      GameHolder holder
  ) {
    this.gameholder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<Player> playersToRemove = new ArrayList<>();
    for (Player player : gameholder.getPlayers().get().getPlayers()) {
      System.out.println("PLAYER CHECKED!!!!"+player.getScore());
      if (player.getScore() <= minScore) {
        playersToRemove.add(player);
      }
    }
    gameholder.removePlayers(playersToRemove);
  }
}
