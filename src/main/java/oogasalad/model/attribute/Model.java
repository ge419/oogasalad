package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import oogasalad.model.constructable.Player;


public class Model {

  public static String printClass(Object ob) {
    return ob.getClass().toString();
  }
  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ObjectMapper objectMapper = new ObjectMapper();
    //List of attributes should be returned by attribute factory using metadata information
    AttributeFactory af = new AttributeFactory();
    Attribute name = af.generate("string", "name", "Bob");
    Attribute score = af.generate("int", "score", 100);
    Attribute tile = af.generate("tile", "tile", 3);
    //Place these attribute objects into String, Attribute Map
    Map<String, Attribute> attrs = new HashMap<>();
    attrs.put(name.getKey(), name);
    attrs.put(score.getKey(), score);
    attrs.put(tile.getKey(), tile);



    Player p = new Player();
    p.setAttributes(attrs);
    System.out.println(p.getAttribute("name"));

    objectMapper.writeValue(new File("data/player.json"), p);

    File file = new File("data/player.json");
    Player PD = objectMapper.readValue(file, Player.class);
    System.out.println(PD.getAttribute("name").getClass());
  }





}
