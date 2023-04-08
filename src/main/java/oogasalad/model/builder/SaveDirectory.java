package oogasalad.model.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class SaveDirectory implements SaveGame{

//  private final String title;
//  private final ? tiles;
//  private final ? rules;
//  private final ? players;
//  private final ? ...

  /**
   * takes DataStorer datastorer as parameter
   */
  public SaveDirectory() {
//    this.title = datastoere.title();
//    this.tiles = datastorer.tiles();
//    this.rules = datastorer.rules();
//    this.players = datastorer.players();
//    this... = datastorer...
  }

  /**
   * save using serializer
   */
  @Override
  public void save(Object o) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    //TODO: figure out what goes in the parameter for AttributeSerializer
    //module.addSerializer(new AttributeSerializer(o));
    mapper.registerModule(module);
    mapper.writeValue(new File("data/title/*.json"), o);
  }
}
