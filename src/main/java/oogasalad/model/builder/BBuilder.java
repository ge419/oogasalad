package oogasalad.model.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javafx.scene.image.Image;
import oogasalad.controller.BuilderController;
import oogasalad.model.AttributeSerializer;
import oogasalad.model.Constructable;
import oogasalad.view.builder.board.BoardImage;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.builder.graphs.ImmutableGraph;
import oogasalad.view.tiles.Tile;
import oogasalad.view.tiles.Tiles;

public class BBuilder implements BBuilderAPI{

  //  private final String title;
//  private final ? tiles;
//  private final ? rules;
//  private final ? ...
  private BuilderController controller;
  private ImmutableGameHolder holder;
  private List<Tile> tileList;
  private List<BoardImage> imageList;
  private String title;

  /**
   * takes DataStorer datastorer as parameter
   */
  public BBuilder(BuilderController controller) {
    this.controller = controller;
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
    extractData(holder);

    //serialize
    //create JSON files
    //save assets
    //once saved added to GameLauncher
  }

  private void extractData(ImmutableGameHolder holder) {
    ImmutableBoardInfo boardInfo =  holder.getBoardInfo();
    ImmutableGraph graph = holder.getTileGraph();
    //title = boardInfo.getTitle();
    int height = boardInfo.getBoardSize().height;
    int width = boardInfo.getBoardSize().width;
    imageList = boardInfo.getBoardImages();
    tileList = graph.getTiles();

  }

  /**
   * settings.json
   * tiles.json
   * rules.json
   * players.json ?
   */

  private void saveSettings() {
    //serialize data for settings.json
  }

  private void saveTiles() throws IOException {
    //serialize data for tiles
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(tileList);
    System.out.println(json);
    mapper.writeValue(new File("data/" + title + "/" + title + "Tiles.json"), tileList);

//    ObjectMapper mapper = new ObjectMapper();
//    SimpleModule module = new SimpleModule();
//    TODO: create a TileSerializer?
//    module.addSerializer(new AttributeSerializer(Constructable.class));
//    mapper.registerModule(module);
//    StringWriter writer = new StringWriter();
//    mapper.writeValue(new File("data/PlayerTest.json"), );
  }

  private void saveAsset() {
    //save images in the given directory
    String filePath = "data/" + title + "/*.json";
  }


  /**
   * Takes the data imported from frontend, changes data ready for serialization
   */
  private void toSerialization() {

  }

  private void toDeserialization() {

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

