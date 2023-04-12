package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.constructable.Tile;
import oogasalad.model.factory.AttributeFactory;
import oogasalad.model.factory.ConstructableFactory;


public class Model {

  public static String printClass(Object ob) {
    return ob.getClass().toString();
  }
  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    MetaLoad db = new MetaLoad();
    System.out.println(db.getAttributeType("id"));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    //List of attributes should be returned by attribute factory using metadata information
    AttributeFactory af = new AttributeFactory();
    ConstructableFactory cf = new ConstructableFactory();


//    File file = new File("data/player.json");
//    Player PD = objectMapper.readValue(file, Player.class);
//    System.out.println(PD.getAttribute("name").getClass());


    List<Tile> tiles = new ArrayList<>();

    List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
    List<List<Double>> positions = new ArrayList<>();
    positions.add(new ArrayList<>(Arrays.asList(100.0, 100.0)));
    positions.add(new ArrayList<>(Arrays.asList(150.0, 100.0)));
    positions.add(new ArrayList<>(Arrays.asList(200.0, 100.0)));
    positions.add(new ArrayList<>(Arrays.asList(200.0, 150.0)));
    positions.add(new ArrayList<>(Arrays.asList(200.0, 200.0)));
    positions.add(new ArrayList<>(Arrays.asList(150.0, 200.0)));
    positions.add(new ArrayList<>(Arrays.asList(100.0, 200.0)));
    positions.add(new ArrayList<>(Arrays.asList(100.0, 150.0)));
    List<Integer> nexts = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 1);
    List<Integer> onLands = Arrays.asList(3, 2, 2, 2, 2, 2, 2, 2);
    List<Integer> afters = Arrays.asList(7, 9, 9, 9, 9, 9, 9, 9);


    for (int i=0; i<ids.size(); i++) {
      Map<String, Attribute> attrs = new HashMap<>();
      Attribute id = af.generate("int", "id", ids.get(i));
      Attribute posTile = af.generate("position", "coordinate", positions.get(i));
      Attribute next = af.generate("tile", "nextTile", nexts.get(i));
      Attribute on = af.generate("tile", "onLand", onLands.get(i));
      Attribute after = af.generate("tile", "afterTurn", afters.get(i));
      attrs.put(id.getKey(), id);
      attrs.put(posTile.getKey(), posTile);
      attrs.put(next.getKey(), next);
      attrs.put(on.getKey(), on);
      attrs.put(after.getKey(), after);
      Tile t = (Tile) cf.generate("TileClass", attrs);
      tiles.add(t);
    }
    for (Tile t : tiles) {
      System.out.println(t.getAttribute("id"));
    }
    objectMapper.writeValue(new File("data/tiles.json"), tiles);

    File file = new File("data/tiles.json");
    Tile[] PD = objectMapper.readValue(file, Tile[].class);
  }





}
