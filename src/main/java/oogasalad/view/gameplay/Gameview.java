package oogasalad.view.gameplay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.events.MonopolyEvent;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.Prompter;
//import oogasalad.model.engine.rules.BuyTileRule;
//import oogasalad.model.engine.rules.DieRule;
import oogasalad.model.engine.rules.DieRule;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.engine.rules.TurnRule;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.pieces.Pieces;
import oogasalad.view.gameplay.pieces.PlayerPiece;
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

  public void renderGameview(Stage primaryStage) throws IOException {
    BorderPane UIroot = new BorderPane();

    //TODO: retrieve which and how many frontend components there are from builder and loop through
    // to render each component

    Renderable board = new Board();
    board.render(UIroot);

    //TODO: PERHAPS FIND A BETTER PLACE FOR THIS
    File file = new File("data/tiles.json");
    SchemaDatabase db = new SchemaDatabase();
    Injector schemaInjector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = schemaInjector.getInstance(ObjectMapper.class);
    BBoard b = objectMapper.readValue(file, BBoard.class);
    ArrayList<Tile> t = new ArrayList<>(b.getTiles());

    tiles = new Tiles(t);
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    Pieces pieces = new Pieces();
    pieces.render(UIroot);
    piece = pieces.getPiece();
    piece.moveToTile(t.get(0));

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
//            injector.getInstance(BuyTileRule.class),
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
      registrar.registerHandler(MonopolyEvent.DIE_ROLLED, this::setDie);
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

  @FunctionalInterface
  interface UiEffect {
    // Present the UI effect, then call the callback once done:
    void present(Runnable callback);
  }

}
