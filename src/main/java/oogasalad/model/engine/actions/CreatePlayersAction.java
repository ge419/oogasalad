package oogasalad.model.engine.actions;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
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

    actionParams.prompter().selectSingleOption(
        "Select number of players", options, this::createPlayers);
  }

  private void createPlayers(IntegerPromptOption selectedPlayers) {
    List<Player> players = new ArrayList<>();
    List<Piece> pieceList = new ArrayList<>();
    for (int i=0; i<selectedPlayers.getValue(); i++) {
      Player player = playerProvider.get();
      Piece piece = pieceProvider.get();
      players.add(player);
      pieceList.add(piece);
      piece.setPlayer(player);
      player.getPieces().add(piece);
    }
    gameholder.setPlayers(new Players(players));
    gameholder.setPieces(pieceList);
//    Pieces pieces = new Pieces(pieceList);
//    for (PlayerPiece piece : pieces.getPieceList()) {
//      piece.moveToTile(gameholder.getBoard().getTiles().get(0));
//    }
    System.out.println(gameholder.getPlayers().getPlayers().get(0).getName());
  }
}
