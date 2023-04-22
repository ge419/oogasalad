package oogasalad.view.gameplay;

import java.io.IOException;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.controller.GameController;
import oogasalad.controller.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.pieces.Pieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class Gameview {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;
  private final GameHolder game;
  private Tiles tiles;
  private Die die;
  private PlayerPiece piece;
  private final boolean waiting = false;
  private final GameController gc;
  private Scene scene;

  public Gameview(GameController gc, GameHolder game) {
    this.gc = gc;
    this.game = game;
  }

  public void renderGameview(Stage primaryStage) throws IOException {
    BorderPane UIroot = new BorderPane();

    //TODO: retrieve which and how many frontend components there are from builder and loop through
    // to render each component

    Renderable board = new Board();
    board.render(UIroot);

    List<Tile> t = gc.loadTiles("data/tiles_test.json");
    tiles = new Tiles(t);
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    List<Player> p = gc.loadPlayers("data/player.json");
    Pieces pieces = new Pieces(this.game.getPlayers().getPlayers());
    pieces.render(UIroot);
    piece = pieces.getPiece();
    piece.moveToTile(t.get(0));

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
