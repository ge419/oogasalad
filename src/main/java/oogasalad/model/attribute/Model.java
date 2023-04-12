package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.Board;
import oogasalad.model.constructable.Tile;


public class Model {

  public static void main(String[] args)
      throws IOException {
//
//    File file = new File("src/main/resources/schemas/datatypes/basicTile.json");
//    AttributeSchema tile = objectMapper.readValue(file, AttributeSchema.class);
//
//    System.out.println(tile);
//

    SchemaDatabase db = new SchemaDatabase();
    Injector injector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db)
    );
    ObjectMapper objectMapper = injector.getInstance(ObjectMapper.class);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    double x = 100.0;
    double y = 100.0;

    List<Tile> tiles = new ArrayList<>();
    for (int i=0; i<8; i++) {
      Tile t = injector.getInstance(Tile.class);
      if (x>=200.0) y+=50.0;
      t.setAttribute("position", new PositionAttribute("position", x, y));
      x+=50;
      tiles.add(t);
    }
    Board board = new Board(tiles);
    objectMapper.writeValue(new File("data/tiles.json"), board);

    File file = new File("data/tiles.json");
    Board bd = objectMapper.readValue(file, Board.class);
    System.out.println(bd.getTiles());

//    File file = new File("data/example/tiles.json");
//    Tile[] TD = objectMapper.readValue(file, Tile[].class);
//    System.out.println(TD);



  }





}
