package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.BoardInfoInterface;
import oogasalad.view.builder.graphs.GraphInterface;

public interface MutableGameInterface {
  public void setTileGraph(GraphInterface graph);
  public void setBoardInfo(BoardInfoInterface board);
}
