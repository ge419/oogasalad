package oogasalad.model.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import oogasalad.controller.BuilderController;
import oogasalad.model.constructable.Board;
import oogasalad.view.builder.board.BoardImage;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.tiles.Tile;
import oogasalad.view.tiles.Tiles;

//TODO: add logger
public class BBuilder implements BBuilderAPI{

  //  private final String title;
//  private final ? tiles;
//  private final ? rules;
//  private final ? ...
  private BuilderController controller;
  //private ImmutableGameHolder holder;
  private List<Tile> tileList;
  private List<BoardImage> imageList;
  private String title;

  /**
   * takes DataStorer datastorer as parameter
   * TODO: BuilderController controller as parameter
   */
  public BBuilder() {
//    this.controller = controller;
//    this.title = datastorer.title();
//    this.tiles = datastorer.tiles();
//    this.rules = datastorer.rules();
//    this... = datastorer...
  }

  /**
   * save JSON files and assets using serializer
   */
  @Override
  public void save(ImmutableGameHolder holder) throws IOException {
    //extractData(holder);
    //saveTiles();

    //serialize
    //create JSON files
    //save assets
    //once saved added to GameLauncher
  }

  /**
   * Extracts data from the frontend builder and backend
   * @param holder
   * @param board
   * @return
   */
  protected Map<String, Object> extractData(ImmutableGameHolder holder, Board board) {
    //List<Map<String, Object>> dataMapList = new ArrayList<>();
    Map<String, Object> dataMap = new HashMap<>();
    ImmutableBoardInfo boardInfo =  holder.getBoardInfo();
    //title = boardInfo.getTitle();
    int height = boardInfo.getBoardSize().height;
    int width = boardInfo.getBoardSize().width;
    //boardDataMap.put("GameTitle", title);
    dataMap.put("BoardWidth", width);
    dataMap.put("BoardHeight", height);
    dataMap.put("Tiles", board.getTiles());
    //dataMap.put("Players", board.getPlayers());
    dataMap.put("Images", boardInfo.getBoardImages());
    return dataMap;
  }

  /**
   * settings.json
   * tiles.json
   * rules.json
   * players.json ?
   */

  private void saveSettings() {
    //serialize data for settings.json


    List<String> pathList = new ArrayList<>();
    for(BoardImage b: imageList) {
      pathList.add(b.imagePath());
    }

  }

  /**
   * Made to return String just for testing purposes.
   * TODO: do not return String, write json file.
   * @return
   * @throws IOException
   */
  protected String saveTiles(List<Tile> tileList) throws IOException {
    //serialize data for tiles
    ObjectMapper mapper = new ObjectMapper();
    String tileJson = mapper.writeValueAsString(tileList);
    return tileJson;
//    System.out.println(tileJson);
//    mapper.writeValue(new File("data/" + title + "/" + title + "Tiles.json"), tileList);
  }

  private void saveAsset() {
    //save images in the given directory
    String directoryPath = "data/" + title + "/";
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
    //module.addSerializer(new AttributeSerializer(o.getClass()));
    mapper.registerModule(module);
    //TODO: replace "title" or get file path from the frontend?
    mapper.writeValue(new File("data/" + "title" + "/*.json"), o);
  }

  @Override
  public void deserialize(Object o) throws IOException {

  }


}

