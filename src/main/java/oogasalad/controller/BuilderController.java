package oogasalad.controller;

import java.io.IOException;
import oogasalad.model.builder.BBuilder;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.board.ImmutableBoardInfo;


/**
 * Temporary controller for front and backend builder
 * TODO: add logger
 */
public class BuilderController implements BuilderControllerInterface{

  private BuilderView front;
  private BBuilder back;

  public BuilderController() {
    //initialize frontend builder
    front = new BuilderView();
    //initialize backend builder
    back = new BBuilder();
  }

  /**
   * Take the ImmutableGameHolder from frontend and call on backend to save
   */
  @Override
  public void save() throws IOException {
    //take holder as parameter
    //back.save(holder);
  }

  /**
   * Take the ImmutableGameHolder from backend and call on frontend to load
   */
  @Override
  public void load() {
    //take holder as parameter?
    front.loadFile();
  }

//  protected void extractData(ImmutableGameHolder holder) {
//    ImmutableBoardInfo boardInfo =  holder.getBoardInfo();
//
//    ImmutableGraph graph = holder.getTileGraph();
//    //title = boardInfo.getTitle();
//    int height = boardInfo.getBoardSize().height;
//    int width = boardInfo.getBoardSize().width;
////    imageList = boardInfo.getBoardImages();
////    tileList = graph.getTiles();
//  }
}
