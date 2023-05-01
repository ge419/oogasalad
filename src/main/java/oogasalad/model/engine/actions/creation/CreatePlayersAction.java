package oogasalad.model.engine.actions.creation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.PlayerCreationEvent;
import oogasalad.model.engine.prompt.IntegerPromptOption;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.exception.ResourceReadException;
import oogasalad.view.gameplay.Gameview;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action for creating players upon user selection after launching game.
 * <p>
 *   User selects from the prompt the number of players to play the game
 *   Added to the engine action queue by {@link oogasalad.model.engine.rules.NumberOfPlayersRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class CreatePlayersAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(CreatePlayersAction.class);
  private final Provider<Player> playerProvider;
  private final Provider<Piece> pieceProvider;
  private final GameHolder gameholder;
  private final int minPlayerNumber;
  private final int maxPlayerNumber;
  private final int numPieces;
  private final ResourceBundle bundle;

  @Inject
  public CreatePlayersAction(
      Provider<Player> playerProvider,
      Provider<Piece> pieceProvider,
      GameHolder holder,
      @Assisted("min") int min,
      @Assisted("max") int max,
      @Assisted("piecePerPlayer") int numPieces,
      @EngineResourceBundle ResourceBundle bundle
  ) {
    this.playerProvider = playerProvider;
    this.pieceProvider = pieceProvider;
    this.gameholder = holder;
    this.minPlayerNumber = min;
    this.maxPlayerNumber = max;
    this.numPieces = numPieces;
    this.bundle = bundle;
  }

  /**
   * executed action: creation of specific number of players, updating GameHolder Players accordingly
   * constructs mandatory integer selection options to be presented by prompter to user
   *
   * <p>
   *   randomize player color and piece image, and binds the corresponding game piece to player
   *   updates the GameHolder with newly generated Players and List of Pieces
   * </p>
   *
   * emits {@link PlayerCreationEvent} that triggers other rules ex. {@link oogasalad.model.engine.rules.TurnRule}
   *
   * @param actionParams incl. emitter, prompter
   */
  @Override
  public void runAction(ActionParams actionParams) {
    List<IntegerPromptOption> options = new ArrayList<>();
    for (int i = this.minPlayerNumber; i <= this.maxPlayerNumber; i++) {
      options.add(new IntegerPromptOption(i));
    }
    actionParams.prompter()
        .selectSingleOption(bundle.getString(getClass().getSimpleName()), options,
            this::createPlayers);
    LOGGER.info("Create Game Players upon User Selection");
    actionParams.emitter().emit(new PlayerCreationEvent());
    LOGGER.info("Emit Player Creation Event");
  }

  private void createPlayers(IntegerPromptOption selectedPlayers) {
    Players players = setPlayers(selectedPlayers.getValue());
    gameholder.setPlayers(players);
    Map<Player, List<Piece>> playerPieceMap = setPlayersPieces(numPieces, players);
    List<Piece> pieceList = new ArrayList<>();
    for (List<Piece> pList : playerPieceMap.values()) {
      pieceList.addAll(pList);
    }
    gameholder.setPieces(pieceList);
    this.gameholder.notifyList();
  }

  private Players setPlayers(int numPlayers) {
    List<Player> playerList = IntStream.range(0, numPlayers)
        .mapToObj(i -> playerProvider.get())
        .collect(Collectors.toList());
    Players gamePlayers = new Players(playerList);
    try {
      gamePlayers.randomize();
    } catch (IOException e) {
      throw new ResourceReadException(e);
    }
    return gamePlayers;
  }

  private Map<Player, List<Piece>> setPlayersPieces(int piecePerPlayer, Players players) {
    Map<Player, List<Piece>> playerPieceMap = players.getList().stream()
        .collect(Collectors.toMap(
            p -> p,
            p -> IntStream.range(0, piecePerPlayer)
                .mapToObj(i -> {
                  Piece piece = pieceProvider.get();
                  piece.setImage(p.getImage());
                  piece.setPlayer(p);
                  piece.setTile(gameholder.getBoard().getTiles().get(0));
                  p.getPieces().add(piece);
                  return piece;
                })
                .collect(Collectors.toList())
        ));
    return playerPieceMap;
  }

}
