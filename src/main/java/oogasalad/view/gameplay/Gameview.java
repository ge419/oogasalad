package oogasalad.view.gameplay;

import com.google.cloud.Tuple;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import org.checkerframework.checker.units.qual.A;

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
  private Players players;
  private Map<Player, List<Piece>> playerPieceMap;

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
    this.players = new Players();
    this.playerPieceMap = new HashMap<>();
  }

  public void renderGameview(Stage primaryStage) throws IOException {
    BorderPane UIroot = new BorderPane();

    Renderable board = new Board();
    board.render(UIroot);
    board.clear(UIroot);

    // TODO: use either Tiles or BBoard, not both!
    tiles = new Tiles(game.getBoard().getTiles());
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    //TODO: retrieve number of players and piece per player from launcher/builder
    // TODO: Dynamically watch players/pieces
    players = setPlayers(3);
    game.setPlayers(players);
    playerPieceMap = setPlayersPieces(1);

    ViewPlayers ViewPlayers = new ViewPlayers(players);
    ViewPlayers.render(UIroot);

    List<Piece> pieceList = new ArrayList<>();
    for (List<Piece> pList : playerPieceMap.values()) {
      pieceList.addAll(pList);

    }
    ViewPieces viewPieces = new ViewPieces(pieceList);
    viewPieces.render(UIroot);

    for (PlayerPiece piece : viewPieces.getPieceList()) {
      piece.moveToTile(game.getBoard().getTiles().get(0));
    }

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

  private Players setPlayers(int numPlayers) throws IOException {
    List<Player> playerList = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
      Player player = playerProvider.get();
      playerList.add(player);
    }
    Players ret = new Players(playerList);
    ret.randomize();
    return ret;
  }

   private Map setPlayersPieces(int piecePerPlayer) {
    Map<Player, List<Piece>> playerPieceMap = new HashMap();
    for (Player p : players.getPlayers()) {
      List<Piece> pieceList = new ArrayList<>();
      for (int j = 0; j < piecePerPlayer; j++) {
        Piece piece = pieceProvider.get();
        piece.setImage(p.getImage());
        piece.setPlayer(p);
        p.getPieces().add(piece);
        pieceList.add(piece);
      }
      playerPieceMap.put(p, pieceList);
    }
    return playerPieceMap;
  }
}
