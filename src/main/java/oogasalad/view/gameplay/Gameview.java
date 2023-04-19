package oogasalad.view.gameplay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.controller.GameController;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.prompt.DualPrompter;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.pieces.Pieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class Gameview {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;

  @JsonProperty("board")
  public String myBoardPath;
  @JsonProperty("choice")
  public String choice;
  private Tiles tiles;
  private Die die;
  private PlayerPiece piece;
  Queue<UiEffect> effects = new LinkedList<>();
  private GameController gc;
  private final boolean waiting = false;

  private DualPrompter prompter;

  public void renderGameview(Stage primaryStage) throws IOException {
    BorderPane UIroot = new BorderPane();

    //TODO: retrieve which and how many frontend components there are from builder and loop through
    // to render each component

    Renderable board = new Board();
    board.render(UIroot);

    gc = new GameController(this.die);

    List<Tile> t = gc.loadTiles("data/tiles_test.json");
    tiles = new Tiles(t);
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    Pieces pieces = new Pieces();
    pieces.render(UIroot);
    piece = pieces.getPiece();
    piece.moveToTile(t.get(0));

    Scene scene = new Scene(UIroot);
    prompter = new DualPrompter(effects, die);

    //TODO: refactor to read from property file
    primaryStage.setTitle("Monopoly");
    primaryStage.setScene(scene);
    primaryStage.setHeight(VIEW_HEIGHT);
    primaryStage.setWidth(VIEW_WIDTH);
    primaryStage.show();



  }
  public void doEffect() {
    if (!effects.isEmpty()) {
      // If there is a pending effect, perform it and do the next one once done
      effects.poll().present(this::doEffect);
    } else {
      // Otherwise run the next action
      gc.run();
    }
  }


  @FunctionalInterface
  public
  interface UiEffect {
    // Present the UI effect, then call the callback once done:
    void present(Runnable callback);
  }

  private class GameviewModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(PlayerPiece.class).toInstance(piece);
      bind(Tiles.class).toInstance(tiles);
      bind(Prompter.class).toInstance(prompter);
    }
  }





}
