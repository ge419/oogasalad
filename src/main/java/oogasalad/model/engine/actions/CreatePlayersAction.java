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
import oogasalad.model.engine.prompt.IntegerPromptOption;
import oogasalad.model.engine.prompt.PromptOption;

public class CreatePlayersAction implements Action {
  private final Provider<Player> playerProvider;
  private GameHolder gameholder;

  @Inject
  public CreatePlayersAction(
      Provider<Player> playerProvider,
      GameHolder holder
      ) {
    this.playerProvider = playerProvider;
    this.gameholder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<IntegerPromptOption> options = new ArrayList<>();
    // TODO get from attributes
    for (int i = 1; i < 4; i++) {
      options.add(new IntegerPromptOption(i));
    }

    actionParams.prompter().selectSingleOption(
        "Select number of players", options, this::createPlayers);
  }

  private void createPlayers(IntegerPromptOption selectedPlayers) {
    List<Player> players = new ArrayList<>();
    for (int i=0; i<selectedPlayers.getValue(); i++) {
      Player player = playerProvider.get();
      players.add(player);
    }
    gameholder.setPlayers(new Players(players));
    System.out.println(gameholder.getPlayers().getPlayers().get(0).getName());
  }
}
