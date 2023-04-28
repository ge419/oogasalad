package oogasalad.view.gameplay;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
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
import oogasalad.view.gameplay.Players.ViewPlayers;
import oogasalad.view.gameplay.pieces.ViewPieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class Gameview {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;
  private final GameHolder game;
  private final Provider<Player> playerProvider;
  private final Provider<Piece> pieceProvider;
  private List<ObjectProperty<Player>> playerObjectProperty;
  private List<ObjectProperty<PlayerPiece>> playerPieceObjectProperty;
  private Tiles tiles;
  private Die die;
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
    this.playerObjectProperty = new ArrayList<>();
    this.playerPieceObjectProperty = new ArrayList<>();
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
    Player player1 = playerProvider.get();
    player1.setColor("0000FF");
    player1.setImage("view.gameplay/piece_1.png");
    // playerPieceObjectProperty.add();
    Player player2 = playerProvider.get();
    player2.setColor("00FF00");
    player2.setImage("view.gameplay/piece_1.png");
    game.setPlayers(new Players(List.of(player1, player2)));

    Piece piece1 = pieceProvider.get();
    piece1.setImage(player1.getImage());
    Piece piece2 = pieceProvider.get();
    piece2.setImage(player2.getImage());
    piece1.setPlayer(player1);
    piece2.setPlayer(player2);
    player1.getPieces().add(piece1);
    player2.getPieces().add(piece2);

    ViewPlayers players = new ViewPlayers(List.of(player1, player2));
    players.render(UIroot);

    ViewPieces viewPieces = new ViewPieces(List.of(piece1, piece2));
    viewPieces.render(UIroot);

    for (PlayerPiece piece : viewPieces.getPieceList()) {
      piece.moveToTile(game.getBoard().getTiles().get(0));
    }

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
