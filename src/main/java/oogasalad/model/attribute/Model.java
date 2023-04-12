package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import oogasalad.model.constructable.Player;


public class Model {
  public static void main(String[] args) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    //List of attributes should be returned by attribute factory using metadata information
    Attribute name = new StringAttribute("name", "Bob");
    Attribute score = new IntAttribute("score", 100);
    Attribute tile = new TileAttribute("tile", 3);
    //Place these attribute objects into String, Attribute Map
    Map<String, Attribute> attrs = new HashMap<>();
    attrs.put(name.getKey(), name);
    attrs.put(score.getKey(), score);
    attrs.put(tile.getKey(), tile);

    Player p = new Player();
    p.setAttributes(attrs);

    objectMapper.writeValue(new File("data/player.json"), p);

    File file = new File("data/player.json");
    Player PD = objectMapper.readValue(file, Player.class);
    System.out.println(PD.getAttribute("name").getClass());
  }





}
