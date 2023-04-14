package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.List;

public class ImmutableBoardInfo implements BoardInfoInterface {

  private final List<BoardImage> myImages;
  private final Dimension myBoardSize;

  public ImmutableBoardInfo(BoardInfoInterface boardInfo) {
    myImages = boardInfo.getBoardImages();
    myBoardSize = new Dimension(boardInfo.getBoardSize());
  }

  @Override
  public List<BoardImage> getBoardImages() {
    return myImages;
  }

  @Override
  public Dimension getBoardSize() {
    return myBoardSize;
  }
}
