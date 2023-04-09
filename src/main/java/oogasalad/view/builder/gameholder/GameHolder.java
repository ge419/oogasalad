package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.BoardInfoInterface;
import oogasalad.view.builder.graphs.Graph;
import oogasalad.view.builder.graphs.GraphInterface;
import oogasalad.view.gameplay.Board;

public class GameHolder implements GameInterface, MutableGameInterface{
  GraphInterface myGraph;
  BoardInfoInterface myBoardInfo;

  public GameHolder(){
    //myGraph = new Graph();
  }

  @Override
  public GraphInterface getTileGraph() {
    return myGraph;
  }

  @Override
  public BoardInfoInterface getBoardInfo() {
    return myBoardInfo;
  }

  @Override
  public void setTileGraph(GraphInterface graph) {
    myGraph = graph;
  }

  @Override
  public void setBoardInfo(BoardInfoInterface board) {
    myBoardInfo = board;
  }
}
