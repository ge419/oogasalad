package oogasalad.controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import oogasalad.controller.builderevents.Dragger;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.view.BuilderFactory;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BuilderModule;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.tiles.BasicTile;
import oogasalad.view.tiles.SimpleViewTile;
import oogasalad.view.tiles.ViewTile;
import oogasalad.view.tiles.ViewTileFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Temporary controller for front and backend builder
 */
public class BuilderController {

  private static final Logger logger = LogManager.getLogger(BuilderController.class);

  // following instances will be removed later
  private String FILE_PATH  = "HARDCODE FILE PATH HERE";
  private String FOLDER_NAME = "CUSTOM1";

  private final BuilderView builderView;
  //  private BBuilder builder;
  private BBoard board;
  private SchemaDatabase db;
  private GameHolder gameHolder;
  private GameHolderBuilder gameHolderBuilder;
  private ViewTileFactory viewTileFactory;

  @Inject
  public BuilderController(
      String injectedLanguage,
      BuilderFactory injectedBuilderFactory,
      ViewTileFactory tileFactory
//      BuilderView view
  ) {
    //TODO: use dependency injection
//    Injector builderInjector = Guice.createInjector(new BuilderModule(givenLanguage, this));
//    builderView = builderInjector.getInstance(BuilderView.class);
//    String theString = givenLanguage;
    builderView = injectedBuilderFactory.makeBuilder(injectedLanguage, this);
    viewTileFactory = tileFactory;
//    builderView = view;
//    builder = new BBuilder();
    System.out.println("made builder");
    db = new SchemaDatabase();
    board = new BBoard();
//    todo: Dominics example code for how to get rules using dependency injection
//    Injector injector = Guice.createInjector(new EngineModule());
//    String rule = "oogasalad.model.engine.rule.TurnRule";
//    Class<Rule> ruleClass = (Class<Rule>) Class.forName(rule);
//    Rule myRule = injector.getInstance(ruleClass);
//    end note
    // new Players(); --> list of players
    gameHolderBuilder = new GameHolderBuilder();
    //gameHolder = gameHolderBuilder.setBoard(board).setPlayers(Optional.ofNullable(board.getPlayers())).build();
  }

  public ViewTile addTile(MouseEvent e) {
    Coordinate pos = new Coordinate((double) e.getX(), (double) e.getY(), 0);
    Tile t = new Tile(db);
    t.setCoordinate(pos);
    board.addTile(t);
    ViewTile tile = viewTileFactory.createDynamicViewTile(t);
//    tile.setId("Tile" + board.getTileCount());
    return tile;
  }

  public void addNext(String currentId, String nextId) {
    board.getById(currentId).get().getNextTileIds().add(nextId);
  }

  public void removeNext(String currentId, String nextId){
    board.getById(currentId).get().getNextTileIds().remove(nextId);
  }

  public void removeTile(String currentId){
    board.remove(currentId);
  }




  public void save(GameHolder holder) {
    // TODO Use SaveManager
    String filePath = FILE_PATH + "/" + FOLDER_NAME;

    //serialize GameHolder to the specified path
//    serialize(holder.getBoard());
//    serialize(holder.getPlayers());

//    try {
    //ImmutableGameHolder holder = front.saveFile();
//      builder.save(holder, board);
//    }
//    catch (IOException e) {
//      logger.error("Failed to save custom built game to JSON file");
//      //TODO: popup for error?
//    }
  }

  /**
   * Take the ImmutableGameHolder from backend and call on frontend to load
   * @param path
   */
  public void load(String path) {
    //take holder as parameter?
    builderView.loadFile();
  }

  public void createEventsForNode(Node node, EventHandler<MouseEvent> mouseClickHandle, Node parent, SimpleBooleanProperty dragToggle) {
    node.setOnMouseClicked(mouseClickHandle);
    Dragger nodeDragger = new Dragger(node, true, MouseButton.PRIMARY, parent);
    dragToggle.addListener((observable, oldValue, newValue) -> {
      nodeDragger.setDraggable(newValue);
    });
  }

  public BuilderView getBuilderView() {
    return builderView;
  }

//  public GameHolder getGameHolder() {
//    return new GameHolderBuilder<>().
//  }
}
