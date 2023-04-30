package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.engine.prompt.IntegerPromptOption;
import oogasalad.view.gameplay.pieces.PlayerPiece;

public class CreatePlayerPieceAction implements Action {
  private final Provider<Piece> pieceProvider;
  private final GameHolder gameholder;

  @Inject
  public CreatePlayerPieceAction(
      Provider<Piece> pieceProvider,
      GameHolder holder
  ) {
    this.pieceProvider = pieceProvider;
    this.gameholder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<IntegerPromptOption> options = new ArrayList<>();
    int maxPossible  = 4;
    for (int i = 1; i < maxPossible; i++) {
      options.add(new IntegerPromptOption(i));
    }

    actionParams.prompter().selectSingleOption(
        "Select number of pieces for a given player", options, this::createPlayerPieces);
  }

  private void createPlayerPieces(IntegerPromptOption selectedPlayerPieceNumber) {
    List<PlayerPiece> playerPieces = new ArrayList<>();
    int selectedNumberOfPieces = selectedPlayerPieceNumber.getValue();
    int numberOfPlayers = gameholder.getPlayers().getList().size();
    int totalNumberOfPieces = numberOfPlayers * selectedNumberOfPieces;
    for (int i=0; i < totalNumberOfPieces; i ++) {
      Piece piece = pieceProvider.get();
      piece.setPlayer(gameholder.getPlayers().getList().get(Math.floorDiv(i, numberOfPlayers)));
      playerPieces.add(new PlayerPiece(piece));
    }

    //gameholder.setPlayers(new Players(players));
    System.out.println(gameholder.getPlayers().getList().get(0).getName());
  }
}
