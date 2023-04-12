package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import oogasalad.model.constructable.AttributeObject;
import oogasalad.model.constructable.Tile;


public class Model {

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

//    //TODO: MADE "basicTile - SCHEMA"
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//    File file = new File("src/main/resources/schemas/datatypes/basicTile.json");
//    AttributeSchema tile = objectMapper.readValue(file, AttributeSchema.class);
//
//    System.out.println(tile);
//    objectMapper.writeValue(new File("data/schemas/basicTile.json"), tile);

    SchemaDatabase db = new SchemaDatabase();
    AttributeObject t = new Tile("basicTile", db);
    System.out.println(t);


  }





}
