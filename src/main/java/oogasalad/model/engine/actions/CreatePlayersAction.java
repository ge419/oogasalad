package oogasalad.model.engine.actions;

import com.fasterxml.jackson.annotation.JacksonInject;
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
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.view.gameplay.pieces.PlayerPiece;

public class CreatePlayersAction implements Action {
  private final Provider<Player> playerProvider;
  private final Provider<Piece> pieceProvider;
  private final GameHolder gameholder;

  @Inject
  public CreatePlayersAction(
      Provider<Player> playerProvider,
      Provider<Piece> pieceProvider,
      GameHolder holder
      ) {
    this.playerProvider = playerProvider;
    this.pieceProvider = pieceProvider;
    this.gameholder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<IntegerPromptOption> options = new ArrayList<>();
    for (int i = gameholder.minPlayer(); i <= gameholder.maxPlayer(); i++) {
      options.add(new IntegerPromptOption(i));
    }
    actionParams.prompter().selectSingleOption("Select number of players", options, this::createPlayers);
    actionParams.emitter().emit(new ChooseNumberOfPlayersEvent());
    //TODO: After figuring out order, bind view player initialize and listen to model player
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

//    for (int i=0; i<selectedPlayers.getValue(); i++) {
//      Player player = playerProvider.get();
//      Piece piece = pieceProvider.get();
//      players.add(player);
//      pieceList.add(piece);
//      piece.setPlayer(player);
//      player.getPieces().add(piece);
//    }
//    gameholder.setPlayers(new Players(players));
//    gameholder.setPieces(pieceList);
//    Pieces pieces = new Pieces(pieceList);
//    for (PlayerPiece piece : pieces.getPieceList()) {
//      piece.moveToTile(gameholder.getBoard().getTiles().get(0));
//    }
  }
  private Players setPlayers(int numPlayers) {
    List<Player> playerList = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
      Player player = playerProvider.get();
      playerList.add(player);
    }
    Players ret = new Players(playerList);
    try {
      ret.randomize();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return ret;
  }

  private Map setPlayersPieces(int piecePerPlayer, Players players) {
    Map<Player, List<Piece>> playerPieceMap = new HashMap();
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
