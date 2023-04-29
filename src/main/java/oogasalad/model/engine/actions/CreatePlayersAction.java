package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.engine.events.ChooseNumberOfPlayersEvent;
import oogasalad.model.engine.prompt.IntegerPromptOption;
import oogasalad.model.exception.ResourceReadException;

public class CreatePlayersAction implements Action {
  private final Provider<Player> playerProvider;
  private final Provider<Piece> pieceProvider;
  private final GameHolder gameholder;
  private final int minPlayerNumber;
  private final int maxPlayerNumber;

  @Inject
  public CreatePlayersAction(
      Provider<Player> playerProvider,
      Provider<Piece> pieceProvider,
      GameHolder holder,
      @Assisted("min") int min,
      @Assisted("max") int max
      ) {
    this.playerProvider = playerProvider;
    this.pieceProvider = pieceProvider;
    this.gameholder = holder;
    this.minPlayerNumber = min;
    this.maxPlayerNumber = max;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<IntegerPromptOption> options = new ArrayList<>();
    for (int i = this.minPlayerNumber; i <= this.maxPlayerNumber; i++) {
      options.add(new IntegerPromptOption(i));
    }
    actionParams.prompter().selectSingleOption("Select number of players", options, this::createPlayers);
    actionParams.emitter().emit(new ChooseNumberOfPlayersEvent());
  }

  private void createPlayers(IntegerPromptOption selectedPlayers) {
    Players players = setPlayers(selectedPlayers.getValue());
    gameholder.setPlayers(players);
    Map<Player, List<Piece>> playerPieceMap = setPlayersPieces(1, players);
    List<Piece> pieceList = new ArrayList<>();
    for (List<Piece> pList : playerPieceMap.values()) {
      pieceList.addAll(pList);
    }
    gameholder.setPieces(pieceList);
    this.gameholder.notifyList();
  }

  private Players setPlayers(int numPlayers) {
    List<Player> playerList = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
      Player player = playerProvider.get();
      playerList.add(player);
    }
    Players gamePlayers = new Players(playerList);
    try {
      gamePlayers.randomize();
    } catch (IOException e) {
      throw new ResourceReadException(e);
    }
    return gamePlayers;
  }

  private Map<Player, List<Piece>> setPlayersPieces(int piecePerPlayer, Players players) {
    Map<Player, List<Piece>> playerPieceMap = new HashMap<>();
    for (Player p : players.getPlayers()) {
      List<Piece> pieceList = new ArrayList<>();
      for (int j = 0; j < piecePerPlayer; j++) {
        Piece piece = pieceProvider.get();
        piece.setImage(p.getImage());
        piece.setPlayer(p);
        piece.setTile(gameholder.getBoard().getTiles().get(0));
        p.getPieces().add(piece);
        pieceList.add(piece);
      }
      playerPieceMap.put(p, pieceList);
    }
    return playerPieceMap;
  }
}
