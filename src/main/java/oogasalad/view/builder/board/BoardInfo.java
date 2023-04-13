package oogasalad.view.builder.board;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.exceptions.InvalidLocationException;
import oogasalad.view.builder.exceptions.InvalidSizeException;

public class BoardInfo implements BoardInfoInterface, MutableBoardInfo {

  private final List<BoardImage> myImages;
  private final Dimension myBoardSize;
  private final ResourceBundle myResource;


  public BoardInfo(ResourceBundle inputResource) {
    myImages = new ArrayList<>();
    myBoardSize = new Dimension();
    myResource = inputResource;
  }

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

  @Override
  public List<BoardImage> getBoardImages() {
    return myImages;
  }

  @Override
  public Dimension getBoardSize() {
    return myBoardSize;
  }

  @Override
  public void setBoardSize(Dimension size) {
    if (size.getWidth() <= 0 || size.getHeight() <= 0) {
      throw new InvalidSizeException(myResource.getString("BadSizeError"));
    }
    myBoardSize.setSize(size);
  }
}
