package oogasalad.controller;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import oogasalad.controller.builderevents.Dragger;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.builder.BBuilder;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.tiles.BasicTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Temporary controller for front and backend builder
 */
public class BuilderController {

  private static final Logger logger = LogManager.getLogger(BuilderController.class);

  private BuilderView builderView;
  private BBuilder builder;
  private BBoard board;
  private SchemaDatabase db;

  public BuilderController() {
    builderView = new BuilderView(this);
    builder = new BBuilder();
    db = new SchemaDatabase();
    board = new BBoard();
  }

  public BasicTile addTile(MouseEvent e) {
    Coordinate pos = new Coordinate((double) e.getX(), (double) e.getY(), 0);
    Tile t = new Tile(db);
    t.setCoordinate(pos);
    board.addTile(t);
    BasicTile tile = new BasicTile(t);
    tile.setId("Tile" + board.getTileCount());
    return tile;
  }

  public void addNext(String currentId, String nextId) {
    board.getById(currentId).getNextTileIds().add(nextId);
  }

  public void removeNext(String currentId, String nextId){
    board.getById(currentId).getNextTileIds().remove(nextId);
  }

  public void removeTile(String currentId){
    board.remove(currentId);
  }


  public void save(ImmutableGameHolder holder) {
    try {
      //ImmutableGameHolder holder = front.saveFile();
      builder.save(holder, board);
    }
    catch (IOException e) {
      logger.error("Failed to save custom built game to JSON file");
      //TODO: popup for error?
    }
  }

  /**
   * Take the ImmutableGameHolder from backend and call on frontend to load
   */
  public void load(String directoryPath) {
    //take holder as parameter?
  }

  public void createEventsForNode(Node node, EventHandler<MouseEvent> mouseClickHandle, Coordinate startLocation) {
    node.setOnMouseClicked(mouseClickHandle);
    Dragger nodeDragger = new Dragger(node, true, startLocation, MouseButton.PRIMARY);
  }

  public BuilderView getBuilderView() {
    return builderView;
  }
}
