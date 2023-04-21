package oogasalad.view.gameplay;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.controller.GameController;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.pieces.Pieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class Gameview {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;
  Queue<UiEffect> effects = new LinkedList<>();
  private Tiles tiles;
  private Die die;
  private PlayerPiece piece;
  private final boolean waiting = false;
  private MyPrompter prompter;
  private final GameController gc;

  public Gameview(GameController gc) {
    this.gc = gc;
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
    Pieces pieces = new Pieces(p);
    pieces.render(UIroot);
    piece = pieces.getPiece();
    piece.moveToTile(t.get(0));

    Scene scene = new Scene(UIroot);
    prompter = new MyPrompter();

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
//      gc.run();
    }
  }


  @FunctionalInterface
  public interface UiEffect {
    // Present the UI effect, then call the callback once done:
    void present(Runnable callback);
  }

  public Die getDie() {
    return this.die;
  }

  public class MyPrompter implements Prompter {

    @Override
    public void rollDice(Runnable callback) {
      effects.add((Runnable afterPresent) ->
          die.setCallback(() -> {
            callback.run();
            afterPresent.run();
          }));
    }

    @Override
    public void yesNoDialog(Consumer<Boolean> callback) {
      ButtonType yes = new ButtonType("Yes", ButtonData.YES);
      ButtonType no = new ButtonType("No", ButtonData.NO);
      Alert alert = new Alert(AlertType.CONFIRMATION,
          "Would you like to buy the property?",
          yes,
          no);

      alert.setTitle("Buy property?");

      effects.add((Runnable afterPresent) -> {
        Optional<ButtonType> result = alert.showAndWait();
        boolean answer = result.orElse(no) == yes;
        callback.accept(answer);
        afterPresent.run();
      });
    }

    @Override
    public <T extends PromptOption> void selectSingleOption(List<? extends T> options,
        Consumer<T> callback) {

    }

    @Override
    public <T extends PromptOption> void selectMultipleOptions(List<? extends T> options,
        Consumer<List<? extends T>> callback) {

    }
  }

}
