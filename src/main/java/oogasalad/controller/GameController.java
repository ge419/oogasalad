package oogasalad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Engine;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.DieRule;
import oogasalad.model.engine.rules.TurnRule;
import oogasalad.view.gameplay.Gameview;

public class GameController {

  private Engine engine;
  private Gameview gv;
  private GameHolder game;

  @Inject
  public GameController(
      Engine engine,
      GameHolder game
  ) {
    gv = new Gameview(this);
    this.engine = engine;
    this.game = game;
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
            injector.getInstance(BuyTileRule.class)
        )
    );
//    run();
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
    //TODO: ObjectMapper should deserialize List of Multiple Players
    List<Player> players = new ArrayList<>();
    File file = new File(filePath);
    SchemaDatabase db = new SchemaDatabase();
    Injector schemaInjector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = schemaInjector.getInstance(ObjectMapper.class);
    Player p = objectMapper.readValue(file, Player.class);
    players.add(p);
    return players;
  }

  public void run() {
    engine.runNextAction();
    gv.doEffect();
  }

}

