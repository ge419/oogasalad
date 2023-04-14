package oogasalad.controller;

import java.io.IOException;
import oogasalad.model.builder.BBuilder;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.exception.FileReaderException;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Temporary controller for front and backend builder
 */
public class BuilderController implements BuilderControllerInterface{

  private static final Logger logger = LogManager.getLogger(BuilderController.class);

  private BuilderView front;
  private BBuilder back;
  private BBoard board;

  public BuilderController() {
    //initialize frontend builder
    front = new BuilderView();
    //initialize backend builder
    back = new BBuilder();
    //TODO: initialize board
    //this will be done in the actual Controller class
  }

  @Override
  public void save(ImmutableGameHolder holder, BBoard board) throws IOException {
    try {
      //ImmutableGameHolder holder = front.saveFile();
      back.save(holder, board);
    }
    catch (IOException e) {
      logger.error("Failed to save custom built game to JSON file");
      //TODO: popup for error?
    }
  }

  /**
   * Take the ImmutableGameHolder from backend and call on frontend to load
   */
  @Override
  public void load() {
    //take holder as parameter?
    front.loadFile();
  }
}
