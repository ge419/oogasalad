package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;

public class CreatePlayersAction implements Action {
  private final Provider<Player> playerProvider;
  private int numPlayers;
  private GameHolder gameholder;

  @Inject
  public CreatePlayersAction(
      Provider<Player> playerProvider,
      int numPlayers,
      GameHolder holder
      ) {
    this.playerProvider = playerProvider;
    this.numPlayers = numPlayers;
    this.gameholder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<Player> players = new ArrayList<>();
    for (int i=0; i<numPlayers; i++) {
      Player player = playerProvider.get();
      players.add(player);
    }
    gameholder.setPlayers(new Players(players));
  }
}
