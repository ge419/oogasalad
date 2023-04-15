package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.List;

/**
 * BoardInfo objects will contain all the images on a given board along with the size of a given
 * board.
 *
 * @author tmh85
 */
public interface BoardInfoInterface {

  /**
   * Returns a list of board images that should appear on the board.
   *
   * @return list of BoardImage objects
   */
  List<BoardImage> getBoardImages();

  /**
   * Returns the width and height of the board.
   *
   * @return Dimension object that contains the width and height of the board.
   */
  Dimension getBoardSize();
}
