package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.BoardInfoInterface;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.graphs.Graph;
import oogasalad.view.builder.graphs.GraphInterface;
import oogasalad.view.builder.graphs.ImmutableGraph;
import oogasalad.view.gameplay.Board;

public class GameHolder implements GameInterface, MutableGameInterface{
  ImmutableGraph myGraph;
  ImmutableBoardInfo myBoardInfo;

  public GameHolder(){
    //myGraph = new Graph();
  }

  @Override
  public ImmutableGraph getTileGraph() {
    return myGraph;
  }

  @Override
  public ImmutableBoardInfo getBoardInfo() {
    return myBoardInfo;
  }

  @Override
  public void setTileGraph(ImmutableGraph graph) {
    myGraph = graph;
  }

  @Override
  public void setBoardInfo(ImmutableBoardInfo board) {
    myBoardInfo = board;
  }
}
