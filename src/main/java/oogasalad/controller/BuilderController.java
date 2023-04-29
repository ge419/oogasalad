package oogasalad.controller;

import com.google.inject.Inject;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.nio.file.Path;
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
import oogasalad.model.constructable.Players;
import oogasalad.model.constructable.Tile;
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
 *
 */
public class BuilderController {

  // following instances will be removed later
  private String FILE_PATH = "HARDCODE FILE PATH HERE";
  private String FOLDER_NAME = "CUSTOM1";

  private static final Logger logger = LogManager.getLogger(BuilderController.class);
  private final BuilderView builderView;
  private SchemaDatabase db;
  private GameHolder gameHolder;
  private GameHolderBuilder gameHolderBuilder;
  private ViewTileFactory viewTileFactory;
  private BBoard board;
  private Players players;
  private SaveManager saveManager;
//  private GameHolderBuilder gameHolderBuilder;

  @Inject
  public BuilderController(
      String injectedLanguage,
      BuilderFactory injectedBuilderFactory,
      ViewTileFactory tileFactory
  ) {
    Injector injector = Guice.createInjector(new BuilderControllerModule(injectedLanguage));
    builderView = injectedBuilderFactory.makeBuilder(injectedLanguage, this);
    viewTileFactory = tileFactory;
    logger.info("created builder");
    db = new SchemaDatabase();
    gameHolder = GameHolder.createDefaultGame();
//    saveManager = new SaveManager();
    board = gameHolder.getBoard();
    players = gameHolder.getPlayers();
    //TODO: fix filePath (get from view)
    String filePath = FILE_PATH + "/" + FOLDER_NAME;
    ObjectMapper mapper = new ObjectMapper();
    saveManager = new SaveManager(Path.of(filePath), mapper);


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

  public void addNext(String currentId, String nextId) {
    board.getById(currentId).get().getNextTileIds().add(nextId);
    logger.info("added next attribute to tile");
  }

  public void removeNext(String currentId, String nextId) {
    board.getById(currentId).get().getNextTileIds().remove(nextId);
    logger.info("removed next attribute from tile");
  }

  public void removeTile(String currentId) {
    board.remove(currentId);
    logger.info("removed tile");
  }

  public void save(GameHolder holder) {
    saveManager.saveGame(holder);
//    ImageList --> loop through and apply saveAsset to all imgages
//    saveManager.saveAsset();
  }

  public void load(String path) {
    gameHolder = saveManager.loadGame();
    //TODO: implement the code below
    //builderView.renderBuilderView(gameHolder);
//    builderView.loadFile();
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

  public PopupForm createPopupForm(GameConstruct construct, ResourceBundle language, Pane location){
    return new PopupForm(construct, language, location);
  }

  private void defaultRules() {
//    saveManager.loadDefRules();
  }

  public List<String> getListOfRules(){
    return List.of(
        "Hello",
        "This",
        "Is",
        "A",
        "Test"
        );
  }

  public List<String> getCurrentTiletypes(){
    return List.of(
        "Wow",
        "Such",
        "Tiletype"
    );
  }
}
