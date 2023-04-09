package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.BoardInfoInterface;
import oogasalad.view.builder.graphs.GraphInterface;

public class ImmutableGameHolder implements GameInterface{

  private BoardInfoInterface myBoardInfo;
  private GraphInterface myGraph;

  public ImmutableGameHolder(GameInterface game){
    myBoardInfo = game.getBoardInfo();
    myGraph = game.getTileGraph();
  }
  @Override
  public GraphInterface getTileGraph() {
    return myGraph;
  }

  @Override
  public BoardInfoInterface getBoardInfo() {
    return myBoardInfo;
  }
}
