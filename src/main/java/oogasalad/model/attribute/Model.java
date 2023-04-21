package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;


public class Model {

  public static void main(String[] args)
      throws IOException {
    SchemaDatabase db = new SchemaDatabase();
    System.out.println(db.getAllSchemaNames());

    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = injector.getInstance(ObjectMapper.class);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

//    double x = 350.0;
//    double y = 85.0;
//
//    List<Tile> tiles = new ArrayList<>();
//    for (int i = 0; i < 11; i++) {
//      Tile t = injector.getInstance(Tile.class);
//      t.setX(x);
//      t.setY(y);
//      x += 50;
//      tiles.add(t);
//    }
//    for (int i = 0; i < 12; i++) {
//      Tile t = injector.getInstance(Tile.class);
//      t.setX(850);
//      t.setY(y);
//      y += 50.0;
//      tiles.add(t);
//    }
//    for (int i = 0; i < 12; i++) {
//      Tile t = injector.getInstance(Tile.class);
//      t.setY(tiles.get(tiles.size() - 1).getY());
//      x -= 50;
//      t.setX(x);
//      tiles.add(t);
//    }
//    for (int i = 0; i < 12; i++) {
//      Tile t = injector.getInstance(Tile.class);
//      y -= 50;
//      t.setY(y);
//      t.setX(x);
//      tiles.add(t);
//    }
//
//    for (int i = 0; i < tiles.size() - 1; i++) {
//      tiles.get(i).getNextTileIds().add(tiles.get(i + 1).getId());
//    }
//    tiles.get(tiles.size() - 1).getNextTileIds().add(tiles.get(0).getId());

    Player player = injector.getInstance(Player.class);
//    BBoard board = new BBoard(tiles);
//    objectMapper.writeValue(new File("data/tiles.json"), board);
    objectMapper.writeValue(new File("data/player.json"), player);

//    File file = new File("data/tiles.json");
//    BBoard bd = objectMapper.readValue(file, BBoard.class);
    System.out.println(player);

  }


}
