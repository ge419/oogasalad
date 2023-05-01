package oogasalad.model.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.board.BoardImage;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BBuilder implements BBuilderAPI {

  private static final Logger log = LogManager.getLogger(BBuilder.class);

  //TODO: implement load
  //TODO: implement save for Rules
  public BBuilder() {

  }

  @Override
  public void save(ImmutableGameHolder holder, BBoard board) throws IOException {
    Map<String, Object> dataMap = extractData(holder, board);
    String filePath = dataMap.get("Path").toString();
    saveSettings(dataMap, filePath);
    saveTiles((List<Tile>) dataMap.get("Tiles"), filePath);
    savePlayers((List<Player>) dataMap.get("Players"), filePath);
    saveAsset((List<BoardImage>) dataMap.get("Images"), filePath);
    //TODO: should the BBuilder trigger the GameLauncher to add newly saved game?
  }

  protected Map<String, Object> extractData(ImmutableGameHolder holder, BBoard board) {
    //List<Map<String, Object>> dataMapList = new ArrayList<>();
    Map<String, Object> dataMap = new HashMap<>();
    ImmutableBoardInfo boardInfo = holder.getBoardInfo();
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
    for (BoardImage b : (List<BoardImage>) dataMap.get("Images")) {
      pathList.add(b.imagePath());
    }
    settingsMap.put("BoardWidth", dataMap.get("BoardWidth"));
    settingsMap.put("BoardHeight", dataMap.get("BoardHeight"));
    settingsMap.put("ImageFIlePaths", pathList);
    serialize(settingsMap, path, "settings");
  }

  protected void saveTiles(List<Tile> tileList, String path) throws IOException {
    //serialize data for tiles
    serialize(tileList, path, "tiles");
  }

  private void savePlayers(List<Player> playersList, String path) throws IOException {
    serialize(playersList, path, "players");
  }

  /**
   * Code for reading and writing image files from link below:
   * https://mkyong.com/java/how-to-write-an-image-to-file-imageio/
   *
   * @param path
   */
  private void saveAsset(List<BoardImage> imageList, String path) throws IOException {
    if (imageList.isEmpty()) {
      return;
    }
    for (BoardImage image : imageList) {
      Path imagePath = Paths.get(image.imagePath());
      BufferedImage bufferedImage = ImageIO.read(new File(image.imagePath()));
      ImageIO.write(bufferedImage, "png",
          new File(path + "/images/" + imagePath.getFileName() + ".png"));
    }
  }

  @Override
  public void load(String path) {
    //deserialize
    //translate data into GameHolder
    //call controller.load()
  }

  private void serialize(Object o, String path, String type) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(new File(path + "/" + type + ".json"), o);
  }

  private GameConstruct deserialize(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    GameConstruct constructableObject = mapper.readValue(file, AbstractGameConstruct.class);
    return constructableObject;
  }


}

