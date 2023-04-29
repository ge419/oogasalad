package oogasalad.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import oogasalad.controller.builderevents.Dragger;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.EditableRule;
import oogasalad.util.SaveManager;
import oogasalad.view.BuilderFactory;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BuilderView;
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
  private SchemaDatabase db;
  private ViewTileFactory viewTileFactory;
  private BBoard board;
  private SaveManager saveManager;
  private final Injector injector;

  public BuilderController(String language, Path saveDir) {
    injector = Guice.createInjector(
        new BuilderControllerModule(language, saveDir)
    );
    injector.getInstance(SaveManager.class).loadGame();

    builderView = injector.getInstance(BuilderFactory.class).makeBuilder(language, this);
    viewTileFactory = injector.getInstance(ViewTileFactory.class);
    logger.info("created builder");
    db = injector.getInstance(SchemaDatabase.class);
    gameHolder = injector.getInstance(GameHolder.class);
    board = gameHolder.getBoard();

    for (Tile tile : board.getTiles()){
      getBuilderView().loadTile(viewTileFactory.createDynamicViewTile(tile));
    }

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
    if (board.getById(currentId).get().getNextTileIds().contains(nextId)){
      logger.info("Tried creating a path that already exists.");
      return false;
    }
    System.out.println("Why hello: " + board.getById(currentId).get().getNextTileIds());
    board.getById(currentId).get().getNextTileIds().add(nextId);
    logger.info("added next attribute to tile");
    return true;
  }

  public boolean removeNext(String currentId, String nextId) {
    if (board.getById(currentId).get().getNextTileIds().contains(nextId)){
      board.getById(currentId).get().getNextTileIds().remove(nextId);
      logger.info("removed next attribute from tile");
      return true;
    }
    else{
      logger.info("tried to remove a next attribute that doesn't exist.");
      return false;
    }
  }

  public void removeTile(String currentId) {
    board.remove(currentId);
    logger.info("removed tile");
  }

  public void save() {
    saveManager.saveGame();
//    ImageList --> loop through and apply saveAsset to all imgages
//    saveManager.saveAsset();
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
    return new PopupForm(construct, language, location);
  }

  private void defaultRules() {
//    saveManager.loadDefRules();
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
    return List.of(
        "Hello",
        "This",
        "Is",
        "A",
        "Test"
    );
  }

  public List<String> getCurrentTiletypes() {
    return List.of(
        "Wow",
        "Such",
        "Tiletype"
    );
  }

  public void makeRulesPopup(String tiletype, String ruleAsString) {
    logger.info("Chose to edit rule " + ruleAsString + " for tiletype " + tiletype);
    // todo: change this to get the rule from whatever string was provided
    EditableRule rule = injector.getInstance(BuyTileRule.class);
    createPopupForm(rule, builderView.getLanguage(), builderView.getPopupPane());
  }

  public void removeRuleFromTiletype(String tiletype, String ruleAsString) {
    logger.info("Trying to remove rule " + ruleAsString +
        " from tiletype " + tiletype);
  }
}
