package oogasalad;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.ConstructableModule;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.prompt.AIPrompter;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.DieMoveRule;
import oogasalad.model.engine.rules.TurnRule;
import oogasalad.util.SaveManager;


public class GenerateSaves {

  public static void main(String[] args)
      throws IOException {
    Path savePath = Path.of("data", "monopoly");
    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        new ConstructableModule(savePath),
        binder -> binder.bind(Prompter.class).toInstance(new AIPrompter()),
        binder -> binder.install(new EngineModule())
    );

    GameHolder gameHolder = GameHolder.createDefaultGame();
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);
    db.setRuleListProperty(gameHolder.rulesProperty());

    gameHolder.setRules(
        List.of(
            injector.getInstance(TurnRule.class),
            injector.getInstance(BuyTileRule.class),
            injector.getInstance(DieMoveRule.class)
        )
    );

    double x = 350.0;
    double y = 85.0;

    List<Tile> tiles = new ArrayList<>();
    for (int i = 0; i < 11; i++) {
      Tile t = injector.getInstance(Tile.class);
      t.setX(x);
      t.setY(y);
      x += 50;
      tiles.add(t);
    }
    for (int i = 0; i < 12; i++) {
      Tile t = injector.getInstance(Tile.class);
      t.setX(850);
      t.setY(y);
      y += 50.0;
      tiles.add(t);
    }
    for (int i = 0; i < 12; i++) {
      Tile t = injector.getInstance(Tile.class);
      t.setY(tiles.get(tiles.size() - 1).getY());
      x -= 50;
      t.setX(x);
      tiles.add(t);
    }
    for (int i = 0; i < 12; i++) {
      Tile t = injector.getInstance(Tile.class);
      y -= 50;
      t.setY(y);
      t.setX(x);
      tiles.add(t);
    }

    for (int i = 0; i < tiles.size() - 1; i++) {
      tiles.get(i).getNextTileIds().add(tiles.get(i + 1).getId());
    }
    tiles.get(tiles.size() - 1).getNextTileIds().add(tiles.get(0).getId());
    BBoard board = new BBoard(tiles);
    gameHolder.setBoard(board);

    SaveManager saveManager = injector.getInstance(SaveManager.class);
    saveManager.saveGame(gameHolder);
  }


}
