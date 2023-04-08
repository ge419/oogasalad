package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.List;
import oogasalad.view.gameplay.Board;

public interface BoardInfoInterface {
  List<BoardImage> getBoardImages();
  Dimension getBoardSize();
}
