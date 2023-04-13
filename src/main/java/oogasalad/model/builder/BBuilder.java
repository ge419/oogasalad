package oogasalad.model.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BBuilder implements BBuilderAPI{

  private static final Logger log = LogManager.getLogger(BuilderController.class);

  public BBuilder() {

  }

  /**
   * save JSON files and assets using serializer
   */
  @Override
  public void save(ImmutableGameHolder holder, Board board) throws IOException {
    Map<String, Object> dataMap = extractData(holder, board);
    String filePath = dataMap.get("Path").toString();
    saveSettings(dataMap, filePath);
    saveTiles((List<Tile>) dataMap.get("Tiles"));
//    savePlayers();
//    saveImages();



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
    //TODO: ask frontend builder to pass directory path as String
    //String directoryPath = boardInfo.getPath();
    int height = boardInfo.getBoardSize().height;
    int width = boardInfo.getBoardSize().width;
    //dataMap.put("GameTitle", title);
    //dataMap.put("Path", directoryPath);
    dataMap.put("BoardWidth", width);
    dataMap.put("BoardHeight", height);
    dataMap.put("Tiles", board.getTiles());
    //dataMap.put("Players", board.getPlayers());
    dataMap.put("Images", boardInfo.getBoardImages());
    return dataMap;
  }

  private void saveSettings(Map<String, Object> dataMap, String path) throws IOException {
    Map<String, Object> settingsMap = new HashMap<>();
    List<String> pathList = new ArrayList<>();
    for(BoardImage b: (List<BoardImage>) dataMap.get("Images")) {
      pathList.add(b.imagePath());
    }
    settingsMap.put("BoardWidth", dataMap.get("BoardWidth"));
    settingsMap.put("BoardHeight", dataMap.get("BoardHeight"));
    settingsMap.put("ImageFIlePaths", pathList);
    serialize(settingsMap, path, "settings");
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
//    String directoryPath = "data/" + title + "/";
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

  private void serialize(Object o, String path, String type) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(new File(path + "/" + type + ".json"), o);
  }

  private void deserialize(Object o) throws IOException {

  }


}

