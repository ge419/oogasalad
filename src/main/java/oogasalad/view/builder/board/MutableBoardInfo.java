package oogasalad.view.builder.board;

import java.awt.Dimension;
import oogasalad.view.Coordinate;

/**
 * Interface used by BoardInfo implementations that keep track of images on the board and the size
 * of the board. Implementing this makes the board info mutable
 *
 * @author Trevon Helm
 */
public interface MutableBoardInfo {

  /**
   * Adds an image to the list of images associated with a game. Throws InvalidSizeException if
   * width or height is negative Throws InvalidLocationException if Coordinate has negative values
   *
   * @param path          String representation of the path to the image
   * @param startLocation Coordinate location of the image on the board
   * @param imageSize     Dimension size of the image (width, height)
   */
  void addImage(String path, Coordinate startLocation, Dimension imageSize);

  /**
   * Sets the size of the board
   *
   * @param size Dimension
   */
  void setBoardSize(Dimension size);
}
