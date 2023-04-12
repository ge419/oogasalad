package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.Tile;


public class Model {

  public static void main(String[] args)
      throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//    File file = new File("src/main/resources/schemas/datatypes/basicTile.json");
//    AttributeSchema tile = objectMapper.readValue(file, AttributeSchema.class);
//
//    System.out.println(tile);
//

    SchemaDatabase db = new SchemaDatabase();
    double x = 100.0;
    double y = 100.0;

    List<Tile> tiles = new ArrayList<>();
    for (int i=0; i<8; i++) {
      Tile t = new Tile("basicTile", db);
      if (x>=200.0) y+=50.0;
      t.setAttribute("position", new PositionAttribute("position", x, y));
      x+=50;
      tiles.add(t);
    }
    objectMapper.writeValue(new File("data/example/tile.json"), tiles.get(0));

    File file = new File("data/example/tile.json");
    Tile TD = objectMapper.readValue(file, Tile.class);
    System.out.println(TD);

//    File file = new File("data/example/tiles.json");
//    Tile[] TD = objectMapper.readValue(file, Tile[].class);
//    System.out.println(TD);



  }





}
