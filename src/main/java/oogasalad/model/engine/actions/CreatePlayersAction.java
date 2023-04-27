package oogasalad.model.engine.actions;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
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
      GameHolder holder,
      @Assisted int numPlayers
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
