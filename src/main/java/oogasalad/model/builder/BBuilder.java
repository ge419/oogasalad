package oogasalad.model.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import oogasalad.model.AttributeSerializer;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.builder.graphs.ImmutableGraph;

public class BBuilder implements BBuilderAPI{

  //  private final String title;
//  private final ? tiles;
//  private final ? rules;
//  private final ? ...

  /**
   * takes DataStorer datastorer as parameter
   */
  public BBuilder() {
//    this.title = datastorer.title();
//    this.tiles = datastorer.tiles();
//    this.rules = datastorer.rules();
//    this... = datastorer...
  }

  /**
   * save JSON files and assets using serializer
   */
  @Override
  public void save(ImmutableGameHolder holder) {
    //TODO: get title?
    ImmutableBoardInfo boardInfo =  holder.getBoardInfo();
    ImmutableGraph graph = holder.getTileGraph();
    boardInfo.getBoardSize();
    boardInfo.getBoardImages();
    graph.getTiles();

    //serialize
    //create JSON files
    //save assets
    //once saved added to GameLauncher
  }

  /**
   * load using deserializer, translate data into GameHolder, call frontend to pass data
   */
  @Override
  public void load() {
    //deserialize
    //translate data into GameHolder
    //call controller.load()
  }

  @Override
  public void serialize(Object o) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    //TODO: figure out what goes in the parameter for AttributeSerializer
    module.addSerializer(new AttributeSerializer(o.getClass()));
    mapper.registerModule(module);
    //TODO: replace "title" or get file path from the frontend?
    mapper.writeValue(new File("data/" + "title" + "/*.json"), o);
  }

  @Override
  public void deserialize(Object o) throws IOException {

  }


}

