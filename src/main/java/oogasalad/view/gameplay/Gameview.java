package oogasalad.view.gameplay;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.controller.GameController;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.pieces.Pieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class Gameview {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;
  private final GameHolder game;
  private final Provider<Player> playerProvider;
  private final Provider<Piece> pieceProvider;
  private Tiles tiles;
  private Die die;
  private PlayerPiece piece;
  private final boolean waiting = false;
  private final GameController gc;
  private Scene scene;

  @Inject
  public Gameview(
      @Assisted GameController gc,
      GameHolder game,
      Provider<Player> playerProvider,
      Provider<Piece> pieceProvider) {
    this.gc = gc;
    this.game = game;
    this.playerProvider = playerProvider;
    this.pieceProvider = pieceProvider;
  }

  public void renderGameview(Stage primaryStage) throws IOException {
    BorderPane UIroot = new BorderPane();

    //TODO: retrieve which and how many frontend components there are from builder and loop through
    // to render each component

    Renderable board = new Board();
    board.render(UIroot);

    // TODO: use either Tiles or BBoard, not both!
    tiles = new Tiles(game.getBoard().getTiles());
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    // TODO: Dynamically watch players/pieces
    game.setPlayers(new Players(List.of(playerProvider.get())));
    Player player = game.getPlayers().getPlayers().get(0);

    Piece piece1 = pieceProvider.get();
    piece1.setPlayer(player);
    player.getPieces().add(piece1);

    Pieces pieces = new Pieces(List.of(piece1));
    pieces.render(UIroot);
    piece = pieces.getPiece();
    piece.moveToTile(game.getBoard().getTiles().get(0));

    //TODO: take in backend player when appropriate attributes are implemented
//    players = new ViewPlayers(b.getPlayers());
//    players.render(UIroot);

    scene = new Scene(UIroot);

    //TODO: refactor to read from property file
    primaryStage.setTitle("Monopoly");
    primaryStage.setScene(scene);
    primaryStage.setHeight(VIEW_HEIGHT);
    primaryStage.setWidth(VIEW_WIDTH);
    primaryStage.show();
  }

  public <T extends Event> void addEventHandler(EventType<T> type, EventHandler<T> action) {
    scene.addEventHandler(type, action);
  }

  public Die getDie() {
    return this.die;
  }


}
