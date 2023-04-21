package oogasalad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.DieRule;
import oogasalad.model.engine.rules.SetDieRule;
import oogasalad.model.engine.rules.TurnRule;
import oogasalad.view.gameplay.Gameview;

public class GameController {

  private final Engine engine;
  private final Gameview gv;
  private final GameHolder game;
  private final PrompterFactory prompterFactory;
  LinkedList<Effect> effects;

  @Inject
  public GameController(
      Engine engine,
      GameHolder game,
      PrompterFactory prompterFactory
  ) {
    gv = new Gameview(this);
    this.effects = new LinkedList<>();
    this.engine = engine;
    this.game = game;
    this.prompterFactory = prompterFactory;
  }

  public Gameview loadGV() throws IOException {
//    File jsonFile = new File(filePath);
//    Injector injector = Guice.createInjector(
//        new ObjectMapperModule(),
//        binder -> binder.bind(GameController.class).toInstance(this)
//    );
//    ObjectMapper objectMapper = injector.getInstance(ObjectMapper.class);
//    gv = objectMapper.readValue(jsonFile, Gameview.class);
    Injector injector = Guice.createInjector(new GameControllerModule(game));
    engine.setRules(
        List.of(
            injector.getInstance(TurnRule.class),
            injector.getInstance(DieRule.class),
            injector.getInstance(BuyTileRule.class),
            new SetDieRule(gv.getDie())
        )
    );
    run();
    return gv;
  }

  public List<Tile> loadTiles(String filePath) throws IOException {
    File file = new File(filePath);
    SchemaDatabase db = new SchemaDatabase();
    Injector schemaInjector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = schemaInjector.getInstance(ObjectMapper.class);
    BBoard b = objectMapper.readValue(file, BBoard.class);
    return new ArrayList<>(b.getTiles());
  }

  public List<Player> loadPlayers(String filePath) throws IOException {
    File file = new File(filePath);
    SchemaDatabase db = new SchemaDatabase();
    Injector schemaInjector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = schemaInjector.getInstance(ObjectMapper.class);
    Players p = objectMapper.readValue(file, Players.class);
    return new ArrayList<>(p.getPlayers());
  }

  public void run() {
    engine.runNextAction();
    this.doEffect();
  }

  public void doEffect() {
    if (!effects.isEmpty()) {
      // If there is a pending effect, perform it and do the next one once done
      effects.poll().present(this::doEffect);
    } else {
      // Otherwise run the next action
      this.run();
    }
  }

}

