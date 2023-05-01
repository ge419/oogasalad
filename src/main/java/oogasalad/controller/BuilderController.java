package oogasalad.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
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
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.BoardImage;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.rules.EditableRule;
import oogasalad.util.SaveManager;
import oogasalad.view.BuilderFactory;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BoardImageTile;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.popupform.PopupForm;
import oogasalad.view.tiles.ViewTile;
import oogasalad.view.tiles.ViewTileFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * BuilderController saves and loads game configuration to the GameBuilder by interacting with
 * BuilderView
 *
 * @author Changmin Shin
 */
public class BuilderController {

  private static final String DEFAULT_STYLESHEET_DIRECTORY = "/view/builder/";
  private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";
  private static final Logger logger = LogManager.getLogger(BuilderController.class);
  private static final String RESOURCE_PATH = "engine/ClassPath";
  private static final ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_PATH);
  private final BuilderView builderView;
  private final GameHolder gameHolder;
  private final GameInfo gameInfo;
  private final Injector injector;
  private final SchemaDatabase db;
  private final ViewTileFactory viewTileFactory;
  private final BBoard board;
  private final SaveManager saveManager;
  private final String gameID;
  private final GameDao gameDao;

  /**
   * Creates {@link GameHolder} and {@link BuilderView} and initializes GameBuilder
   *
   * @param language
   * @param gameID
   * @param gameDao
   */
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
  }

  /**
   * Adds tile to the BBoard and creates ViewTile in the BuilderView
   *
   * @param e MouseEvent
   * @return ViewTile created
   */
  public ViewTile addTile(MouseEvent e) {
    Coordinate pos = new Coordinate(e.getX(), e.getY(), 0.0);
    Tile t = new Tile(db);
    t.setCoordinate(pos);
    board.addTile(t);
    ViewTile tile = viewTileFactory.createDynamicViewTile(t);
//    tile.setId("Tile" + board.getTileCount());
    logger.info("added tile");
    return tile;
  }

  /**
   * Adds next attribute in a tile
   *
   * @param currentId Current tile id
   * @param nextId    Next tile id
   * @return true if added, false if not added
   */
  public boolean addNext(String currentId, String nextId) {
    if (board.getById(currentId).get().getNextTileIds().contains(nextId)) {
      logger.info("Tried creating a path that already exists.");
      return false;
    }
    board.getById(currentId).get().getNextTileIds().add(nextId);
    logger.info("added next attribute to tile");
    return true;
  }

  /**
   * Removes next attribute in a tile
   *
   * @param currentId Current tile id
   * @param nextId    Next tile id
   * @return true if removed, false if not removed
   */
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

  /**
   * Removes tile from the BBoard
   *
   * @param currentId ID of the tile that is to be removed
   */
  public void removeTile(String currentId) {
    board.remove(currentId);
    logger.info("removed tile");
  }

  /**
   * Saves the game configuration as JSON files
   */
  public void save() {
    injector.getInstance(SaveManager.class).saveGame();
  }

  public void createEventsForNode(Node node, EventHandler<MouseEvent> mouseClickHandle, Node parent,
      SimpleBooleanProperty dragToggle) {
    node.setOnMouseClicked(mouseClickHandle);
    Dragger nodeDragger = new Dragger(node, true, MouseButton.PRIMARY, parent);
    dragToggle.addListener((observable, oldValue, newValue) -> {
      nodeDragger.setDraggable(newValue);
    });
  }

  /**
   * Getter method for BuilderView
   *
   * @return BuilderView
   */
  public BuilderView getBuilderView() {
    return builderView;
  }

  /**
   * Creates popup form given a GameConstruct object
   *
   * @param construct GameConstruct object to be edited by the popup
   * @param language  Language properties file to access to
   * @param location  Location of the popup form
   * @return
   */
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

  /**
   * Takes the names of the default rules stored in properties file
   *
   * @return List of default rule names
   */
  public List<String> getListOfRules() {
    return resources.keySet().stream().toList();
  }

  /**
   * Takes the name of a rule and returns the corresponding rule class
   *
   * @param ruleClass Name of the rule
   * @return Name of the corresponding rule class
   */
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

  /**
   * Creates a new rule and triggers popup form in the BuilderView to edit the rule
   *
   * @param ruleAsString The name of the rule object to be created
   * @throws Exception
   */
  public void makeRulesPopup(String ruleAsString) throws Exception {
    try {
      logger.info("Chose to edit rule " + ruleAsString);
      Class<? extends EditableRule> clazz = (Class<? extends EditableRule>) Class.forName(
          ruleAsString);
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
    return !(tile.getHeight() > boardHeight) && !(tile.getWidth() > boardWidth);
  }

  /**
   * <p>Creates a board image tile object for the frontend, while also placing a
   * backend image tile in the object.</p>
   * <p>This method will also use the save manager to save the asset to the game itself.</p>
   *
   * @param imagePath path of the image
   * @return a boardimagetile object
   */
  public Optional<BoardImageTile> createBoardImage(Path imagePath) {
    System.out.println("This is our image path: " + imagePath);
    BoardImage backendImage = new BoardImage(db);
    Coordinate coordinate = new Coordinate(0, 0, 0);
    backendImage.setCoordinate(coordinate);

    if (!savePathAsAsset(imagePath)) {
      return Optional.empty();
    }
    return Optional.of(new BoardImageTile(backendImage));
  }

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

  /**
   * Updates width of the board and saves it in the GameInfo in GameHolder
   *
   * @param width Width in double
   */
  public void updateWidth(double width) {
    gameHolder.getGameInfo().setWidth(width);
  }

  /**
   * Updates width of the board and saves it in the GameInfo in GameHolder
   *
   * @param height Height in double
   */
  public void updateHeight(double height) {
    gameHolder.getGameInfo().setHeight(height);
  }

  /**
   * Getter method for GameHolder Utilized only for testing purposes
   *
   * @return GameHolder
   */
  protected GameHolder getGameHolder() {
    return gameHolder;
  }

  public void saveInfo(String genre, String description) {
    Map<String, Object> game = new HashMap<>();
    game.put(GameSchema.GENRE.getFieldName(), genre);
    game.put(GameSchema.DESCRIPTION.getFieldName(), description);
    gameDao.updateGame(gameID, game);
  }

}
