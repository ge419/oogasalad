package oogasalad.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.builder.BBuilder;
import oogasalad.model.constructable.Board;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.builder.graphs.ImmutableGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Temporary controller for front and backend builder
 */
public class BuilderController implements BuilderControllerInterface{

  private static final Logger log = LogManager.getLogger(BuilderController.class);

  private BuilderView front;
  private BBuilder back;
  private Board board;

  public BuilderController() {
    //initialize frontend builder
    front = new BuilderView();
    //initialize backend builder
    back = new BBuilder();
    //TODO: initialize board
    //this will be done in the actual Controller class
  }

  /**
   * Take the ImmutableGameHolder from frontend and call on backend to save
   * TODO: remove ImmutableGameHolder parameter, replace by creating getter method in BuildView?
   */
  @Override
  public void save(ImmutableGameHolder holder, Board board) throws IOException {

//    holder = front.getImmutableGameHolder();

    //TODO: change API to match parameters
    //back.save(holder, board);
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
