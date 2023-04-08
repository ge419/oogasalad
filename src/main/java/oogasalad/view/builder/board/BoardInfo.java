package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import oogasalad.view.Coordinate;

public class BoardInfo implements BoardInfoInterface, MutableBoardInfo{

  private final List<BoardImage> myImages;
  private final Dimension myBoardSize;

  public BoardInfo(){
    myImages = new ArrayList<>();
    myBoardSize = new Dimension();
  }

  @Override
  public void setImage(String path, Coordinate startLocation, Dimension imageSize){
    myImages.add(new BoardImage(path, startLocation, imageSize));
  }

  @Override
  public void setBoardSize(Dimension size){
    myBoardSize.setSize(size);
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
