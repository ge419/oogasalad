package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.List;
/**
 * Class used to keep track of images in the game and the board size
 * @author Trevon Helm
 */
public class ImmutableBoardInfo implements BoardInfoInterface {

  private final List<BoardImage> myImages;
  private final Dimension myBoardSize;
  /**
   * Creates a new instance of BoardInfo with current images and size
   * @param boardInfo BoardInfoInterface used to get images and size
   */
  public ImmutableBoardInfo(BoardInfoInterface boardInfo) {
    myImages = boardInfo.getBoardImages();
    myBoardSize = new Dimension(boardInfo.getBoardSize());
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
}
