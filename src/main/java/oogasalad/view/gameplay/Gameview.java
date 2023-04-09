package oogasalad.view.gameplay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.events.TurnEvent;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.DieRule;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.engine.rules.TurnRule;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.pieces.Pieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.BasicTile;
import oogasalad.view.tiles.Tiles;

public class Gameview {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1200;
  private final int VIEW_HEIGHT = 800;

  @JsonProperty("board")
  public String myBoardPath;

  @JsonProperty("choice")
  public String choice;
  private Tiles tiles;
  private Die die;
  private PlayerPiece piece;
  private boolean waiting = false;

  Queue<UiEffect> effects = new LinkedList<>();
  private Engine engine;
  private MyPrompter prompter;

  public void renderGameview(Stage primaryStage) {
    BorderPane UIroot = new BorderPane();

    //TODO: retrieve which and how many frontend components there are from builder and loop through
    // to render each component

    Renderable board = new Board();
    board.render(UIroot);

    tiles = new Tiles();
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    Pieces pieces = new Pieces();
    pieces.render(UIroot);
    piece = pieces.getPiece();
    piece.moveToTile(tiles.getTile(1));

    Scene scene = new Scene(UIroot);
    
    //TODO: refactor to read from property file
    primaryStage.setTitle("Monopoly");
    primaryStage.setScene(scene);
    primaryStage.setHeight(VIEW_HEIGHT);
    primaryStage.setWidth(VIEW_WIDTH);
    primaryStage.show();

    Injector injector = Guice.createInjector(new GameviewModule());
    engine = injector.getInstance(Engine.class);
    engine.setRules(
        List.of(
            injector.getInstance(TurnRule.class),
            injector.getInstance(DieRule.class),
            new SetDieRule()
        )
    );
    prompter = new MyPrompter();
    run();
  }

  void run() {
    engine.runNextAction(prompter);
    doEffect();
  }

  void doEffect() {
    if (!effects.isEmpty()) {
      // If there is a pending effect, perform it and do the next one once done
      effects.poll().present(this::doEffect);
    } else {
      // Otherwise run the next action
      run();
    }
  }


  private class GameviewModule extends AbstractModule {
    @Override
    protected void configure() {
      install(new EngineModule());
      bind(PlayerPiece.class).toInstance(piece);
      bind(Tiles.class).toInstance(tiles);
    }
  }

  private class SetDieRule implements Rule {

    @Override
    public void registerEventHandlers(EventRegistrar registrar) {
      registrar.registerHandler(TurnEvent.DIE_ROLLED, this::setDie);
    }

    private void setDie(EventHandlerParams eventHandlerParams) {
      IntAttribute attr = IntAttribute.from(eventHandlerParams.event().attributeMap().get("value"));
      die.rollDice(attr.getValue());
    }
  }

  private class MyPrompter implements Prompter {

    @Override
    public void rollDice(Runnable callback) {
      effects.add((Runnable afterPresent) ->
          die.setCallback(() -> {
            callback.run();
            afterPresent.run();
          }));
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

  @FunctionalInterface
  interface UiEffect {
    // Present the UI effect, then call the callback once done:
    void present(Runnable callback);
  }

}
