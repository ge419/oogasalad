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
import oogasalad.model.constructable.Tile;


public class Model {

  public static ArrayList<String> nextIds = new ArrayList<>();

  public static void main(String[] args)
      throws IOException {
//
//    File file = new File("src/main/resources/schemas/datatypes/basicTile.json");
//    AttributeSchema tile = objectMapper.readValue(file, AttributeSchema.class);
//
//    System.out.println(tile);
//

    SchemaDatabase db = new SchemaDatabase();
    Tile t2 = new Tile(db);

    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = injector.getInstance(ObjectMapper.class);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    double x = 350.0;
    double y = 85.0;

    List<Tile> tiles = new ArrayList<>();
    for (int i = 0; i < 11; i++) {
      Tile t = injector.getInstance(Tile.class);
//      if (x>=200.0) y+=50.0;
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
    BBoard board = new BBoard(tiles, List.of());
    objectMapper.writeValue(new File("data/tiles.json"), board);

    File file = new File("data/tiles.json");
    BBoard bd = objectMapper.readValue(file, BBoard.class);
    System.out.println(bd.getTiles());

    //onPressNewTile() SchemaDatabase db = new SchemaDatabase()
    //Tile t = new Tile(db);
    //tiles.add(t) 보드에 타일 더하기
    //return t;

    //onEditAttribute(value)
    //t.setAttribute(변형값, 실제값)

    //tiles

  }


}
