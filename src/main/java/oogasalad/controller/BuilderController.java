package oogasalad.controller;

import java.io.IOException;
import java.util.Optional;
import javafx.scene.input.MouseEvent;
import oogasalad.model.attribute.SchemaDatabase;
//import oogasalad.model.builder.BBuilder;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.gameholder.GameHolder;
import oogasalad.view.builder.gameholder.GameHolderBuilder;
import oogasalad.view.tiles.BasicTile;
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

  private BuilderView builderView;
//  private BBuilder builder;
  private BBoard board;
  private SchemaDatabase db;
  private GameHolder gameHolder;
  private GameHolderBuilder gameHolderBuilder;

  public BuilderController() {
    //TODO: use dependency injection
    builderView = new BuilderView(this);
//    builder = new BBuilder();
    db = new SchemaDatabase();
    board = new BBoard();
    // new Players(); --> list of players
    gameHolderBuilder = new GameHolderBuilder();
    gameHolder = gameHolderBuilder.setBoard(board).setPlayers(
        Optional.ofNullable(board.getPlayers())).build();
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


  public void save(GameHolder holder) {
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
   */
  public void load() {
    //take holder as parameter?
    builderView.loadFile();
  }

  public BuilderView getBuilderView() {
    return builderView;
  }

//  public GameHolder getGameHolder() {
//    return new GameHolderBuilder<>().
//  }
}
