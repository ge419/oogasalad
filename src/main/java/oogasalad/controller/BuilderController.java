package oogasalad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import oogasalad.controller.builderevents.Dragger;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.database.schema.GameSchema;
import oogasalad.model.attribute.FileReader;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.BoardImage;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.EditableRule;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.exception.FileReaderException;
import oogasalad.model.exception.ResourceReadException;
import oogasalad.util.SaveManager;
import oogasalad.view.BuilderFactory;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BoardImageTile;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.ErrorHandler;
import oogasalad.view.builder.popupform.PopupForm;
import oogasalad.view.tiles.ViewTile;
import oogasalad.view.tiles.ViewTileFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Controller for GameBuilder
 */
public class BuilderController {

  private static final String DEFAULT_STYLESHEET_DIRECTORY = "/view/builder/";
  private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";
  private static final Logger logger = LogManager.getLogger(BuilderController.class);
  private final BuilderView builderView;
  private final GameHolder gameHolder;
  private final GameInfo gameInfo;
  private SchemaDatabase db;
  private ViewTileFactory viewTileFactory;
  private BBoard board;
  private SaveManager saveManager;
  private final Injector injector;
  private String gameID;
  private GameDao gameDao;
  private static final String RULE_NAME_KEY = "name";
  private static final String RULE_DESCRIPTION_KEY = "description";
  private static final String RESOURCE_PATH = "engine/ClassPath";
  private static final ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH);;

  public BuilderController(String language, String gameID, GameDao gameDao) {
    injector = Guice.createInjector(
        new BuilderControllerModule(language, gameID, gameDao)
    );
    injector.getInstance(SaveManager.class).loadGame();
    this.gameID = gameID;
    this.gameDao = gameDao;
    builderView = injector.getInstance(BuilderFactory.class).makeBuilder(language, this);
    viewTileFactory = injector.getInstance(ViewTileFactory.class);
    logger.info("created builder");
    db = injector.getInstance(SchemaDatabase.class);
    gameHolder = injector.getInstance(GameHolder.class);
    board = gameHolder.getBoard();
    gameInfo = gameHolder.getGameInfo();
    saveManager = injector.getInstance(SaveManager.class);

    loadIntoBuilder();
//    readDefaultRules();

//    todo: Dominics example code for how to get rules using dependency injection
//    Injector injector = Guice.createInjector(new EngineModule());
//    String rule = "oogasalad.model.engine.rule.TurnRule";
//    Class<Rule> ruleClass = (Class<Rule>) Class.forName(rule);
//    Rule myRule = injector.getInstance(ruleClass);
//    end note
    // new Players(); --> list of players
  }


  public ViewTile addTile(MouseEvent e) {
    Coordinate pos = new Coordinate((double) e.getX(), (double) e.getY(), 0.0);
    Tile t = new Tile(db);
    t.setCoordinate(pos);
    board.addTile(t);
    ViewTile tile = viewTileFactory.createDynamicViewTile(t);
//    tile.setId("Tile" + board.getTileCount());
    logger.info("added tile");
    return tile;
  }

  public boolean addNext(String currentId, String nextId) {
    if (board.getById(currentId).get().getNextTileIds().contains(nextId)) {
      logger.info("Tried creating a path that already exists.");
      return false;
    }
    board.getById(currentId).get().getNextTileIds().add(nextId);
    logger.info("added next attribute to tile");
    return true;
  }

  public boolean removeNext(String currentId, String nextId) {
    if (board.getById(currentId).get().getNextTileIds().contains(nextId)) {
      board.getById(currentId).get().getNextTileIds().remove(nextId);
      logger.info("removed next attribute from tile");
      return true;
    } else {
      logger.info("tried to remove a next attribute that doesn't exist.");
      return false;
    }
  }

  public void removeTile(String currentId) {
    board.remove(currentId);
    logger.info("removed tile");
  }

  public void save() {
    injector.getInstance(SaveManager.class).saveGame();
  }

  public void saveImage(Path path) throws IOException {
    injector.getInstance(SaveManager.class).saveAsset(path);
  }

  public void createEventsForNode(Node node, EventHandler<MouseEvent> mouseClickHandle, Node parent,
      SimpleBooleanProperty dragToggle) {
    node.setOnMouseClicked(mouseClickHandle);
    Dragger nodeDragger = new Dragger(node, true, MouseButton.PRIMARY, parent);
    dragToggle.addListener((observable, oldValue, newValue) -> {
      nodeDragger.setDraggable(newValue);
    });
  }

  public BuilderView getBuilderView() {
    return builderView;
  }

  public PopupForm createPopupForm(GameConstruct construct, ResourceBundle language,
      Pane location) {
    return new PopupForm(construct, language, location, injector);
  }

  /**
   * Creates a map of Key:Value pairs corresponding to Name:Filepath of all CSS files in the default
   * stylesheet directory
   *
   * @return Map<String fileName, String filePath>
   */
  public Map<String, String> getThemeOptions() {
    Map<String, String> themeMap = new HashMap<>();

    File dir = getStylesheetDirectory();
    String ext = "css";
    String[] fileList = getFilteredFiles(dir, ext);

    for (String name : fileList) {
      String path = DEFAULT_STYLESHEET_DIRECTORY + name;
      themeMap.put(name, path);
    }
    return themeMap;
  }

  private File getStylesheetDirectory() {
    String resourceDirPath = getClass().getResource(DEFAULT_STYLESHEET).getPath();
    File dir = new File(resourceDirPath).getParentFile();
    return dir;
  }

  private String[] getFilteredFiles(File directory, String extension) {
    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(extension);
      }
    };
    return directory.list(filter);
  }

  public List<String> getListOfRules() {
    return resources.keySet().stream().toList();
  }

  public String getClassForRule(String ruleClass) {
    return resources.getString(ruleClass);
  }


  public List<String> getCurrentTiletypes() {
    return List.of(
        "Wow",
        "Such",
        "Tiletype"
    );
  }

  public void makeRulesPopup(String ruleAsString) throws Exception {
    try {
      logger.info("Chose to edit rule " + ruleAsString);
      Class<? extends EditableRule> clazz = (Class<? extends EditableRule>) Class.forName(ruleAsString);
      EditableRule rule = injector.getInstance(clazz);
      logger.info("New rule created");
      createPopupForm(rule, builderView.getLanguage(), builderView.getPopupPane());
      gameHolder.getRules().add(rule);
      logger.info("New rule added to GameHolder");
    } catch (ClassNotFoundException e) {
      logger.fatal("Failed to create rule classes", e);
      throw new Exception(e);
    }
  }

  public void removeRuleFromTiletype(String tiletype, String ruleAsString) {
    logger.info("Trying to remove rule " + ruleAsString +
        " from tiletype " + tiletype);
  }

  private void loadIntoBuilder() {
    boolean lostTiles = false;
    getBuilderView().loadBoardSize(gameInfo.getWidth(), gameInfo.getHeight());

    for (Tile tile : board.getTiles()) {
      if (!checkTileValidity(tile, gameInfo.getWidth(), gameInfo.getHeight())) {
        logger.warn("Tried to load an invalid tile! Coordinate: " +
            tile.getCoordinate().toString() + " Width: " + tile.getWidth() + " Height: "
            + tile.getHeight());
        lostTiles = true;
        continue;
      }
      getBuilderView().loadTile(viewTileFactory.createDynamicViewTile(tile));
    }

    if (lostTiles) {
      getBuilderView().showError("InvalidTilesLoadedError");
    }
  }

  /**
   * <p>Checks if a tile is within the bounds of the given board or not.</p>
   *
   * @param tile        tile we are checking
   * @param boardWidth  width of the board
   * @param boardHeight height of the board
   * @return true if valid, false if not
   */
  private boolean checkTileValidity(Tile tile, double boardWidth, double boardHeight) {
    Coordinate tileCoordinate = tile.getCoordinate();
    if (tileCoordinate.getXCoor() - tile.getWidth() > boardWidth) {
      return false;
    }
    if (tileCoordinate.getYCoor() - tile.getHeight() > boardHeight) {
      return false;
    }
    if (tile.getHeight() > boardHeight || tile.getWidth() > boardWidth) {
      return false;
    }

    return true;
  }

  /**
   * <p>Creates a board image tile object for the frontend, while also placing a
   * backend image tile in the object.</p>
   * <p>This method will also use the save manager to save the asset to the game itself.</p>
   *
   * @param imagePath path of the image
   * @return a boardimagetile object
   */
  public Optional<BoardImageTile> createBoardImage(Path imagePath){
      System.out.println("This is our image path: " + imagePath);
      BoardImage backendImage = new BoardImage(db);
      Coordinate coordinate = new Coordinate(0, 0, 0);
      backendImage.setCoordinate(coordinate);

      if (!savePathAsAsset(imagePath)) {
        return Optional.empty();
      }
//      backendImage.imageAttribute().valueProperty()
//          .addListener(((observable, oldValue, newValue) -> {
//            if (!savePathAsAsset(newValue)) {
//              backendImage.setImage(oldValue);
//            }
//          }));
      return Optional.of(new BoardImageTile(backendImage));
  }
//  private void readDefaultRules() {
//    try {
//      rules = new HashMap<>();
//      for (File file : FileReader.readFiles("rules")) {
//        EditableRule rule = readRulesFile(file.toPath());
//        String name = StringAttribute.from(rule.getAttribute(RULE_NAME_KEY).get()).getValue();
//        String desc = StringAttribute.from(rule.getAttribute(RULE_DESCRIPTION_KEY).get())
//            .getValue();
//        rules.putIfAbsent(name, desc);
//      }
//    } catch (FileReaderException | IOException e) {
//      logger.fatal("Failed to read resource rule files", e);
//      throw new ResourceReadException(e);
//    }
//  }
//
//  private EditableRule readRulesFile(Path path) throws IOException {
//    ObjectMapper mapper = new ObjectMapper();
//    return mapper.readValue(path.toFile(), EditableRule.class);
//  }

  private boolean savePathAsAsset(Path path) {
    try {
      saveManager.saveAsset(path);
      return true;
    } catch (IOException e) {
      getBuilderView().showError("ImagePathSaveError");
      return false;
    }
  }

  public String getGameID() {
    return gameID;
  }

  public void updateWidth(double width) {
    gameHolder.getGameInfo().setWidth(width);
  }

  public void updateHeight(double height) {
    gameHolder.getGameInfo().setHeight(height);
  }
//  public void saveInfo(String genre, String description) {
//    Map<String, Object> game = new HashMap<>();
//    game.put(GameSchema.GENRE.getFieldName(), genre);
//    game.put(GameSchema.DESCRIPTION.getFieldName(), description);
//    gameDao.updateGame(gameID, game);
//  }
}
