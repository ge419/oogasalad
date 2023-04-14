package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.graphs.ImmutableGraph;

public class GameHolder implements GameInterface, MutableGameInterface {

  ImmutableGraph myGraph;
  ImmutableBoardInfo myBoardInfo;

  public GameHolder() {
    //myGraph = new Graph();
  }

  @Override
  public ImmutableGraph getTileGraph() {
    return myGraph;
  }

  @Override
  public void setTileGraph(ImmutableGraph graph) {
    myGraph = graph;
  }

  @Override
  public ImmutableBoardInfo getBoardInfo() {
    return myBoardInfo;
  }

  @Override
  public void setBoardInfo(ImmutableBoardInfo board) {
    myBoardInfo = board;
  }
}
