package oogasalad.view.builder.board;

import java.awt.Dimension;
import oogasalad.view.Coordinate;

public interface MutableBoardInfo {

  void setImage(String path, Coordinate startLocation, Dimension imageSize);
  void setBoardSize(Dimension size);
}
