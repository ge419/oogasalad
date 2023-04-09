package oogasalad.view.builder.gameholder;

import oogasalad.view.builder.board.BoardInfoInterface;
import oogasalad.view.builder.graphs.GraphInterface;

public interface GameInterface {

  public GraphInterface getTileGraph();
  public BoardInfoInterface getBoardInfo();
}
