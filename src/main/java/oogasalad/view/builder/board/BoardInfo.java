package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.exceptions.InvalidLocationException;
import oogasalad.view.builder.exceptions.InvalidSizeException;

/**
 * Class used to keep track of images in the game and the board size
 * @author Trevon Helm
 */
public class BoardInfo implements BoardInfoInterface, MutableBoardInfo {

  private final List<BoardImage> myImages;
  private final Dimension myBoardSize;
  private final ResourceBundle myResource;

  /**
   * Creates a new instance of BoardInfo with no images, and a size of 0
   * @param inputResource ResourceBundle used to get error messages for the user
   */
  public BoardInfo(ResourceBundle inputResource) {
    myImages = new ArrayList<>();
    myBoardSize = new Dimension();
    myResource = inputResource;
  }

  /**
   * Adds an image to the list of images associated with a game.
   * Throws InvalidSizeException if width or height is negative
   * Throws InvalidLocationException if Coordinate has negative values
   * @param path String representation of the path to the image
   * @param startLocation Coordinate location of the image on the board
   * @param imageSize Dimension size of the image (width, height)
   */
  @Override
  public void addImage(String path, Coordinate startLocation, Dimension imageSize) {
    if (imageSize.getWidth() <= 0 || imageSize.getHeight() <= 0) {
      throw new InvalidSizeException(myResource.getString("BadSizeError"));
    }
    if (startLocation.getXCoor() < 0 || startLocation.getYCoor() < 0) {
      throw new InvalidLocationException(myResource.getString("NegativeCoordinateError"));
    }
    myImages.add(new BoardImage(path, startLocation, imageSize));
  }

  /**
   * Returns the list of BoardImages associated with the game
   * @return List of BoardImage
   */
  @Override
  public List<BoardImage> getBoardImages() {
    return myImages;
  }

  /**
   * Returns the size of the board as a Dimension
   * @return Dimension size of board
   */
  @Override
  public Dimension getBoardSize() {
    return myBoardSize;
  }

  /**
   * Sets the size of the board
   * @param size Dimension
   */
  @Override
  public void setBoardSize(Dimension size) {
    if (size.getWidth() <= 0 || size.getHeight() <= 0) {
      throw new InvalidSizeException(myResource.getString("BadSizeError"));
    }
    myBoardSize.setSize(size);
  }
}
