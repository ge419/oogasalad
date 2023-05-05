package oogasalad.view.builder;

import javafx.scene.image.ImageView;
import oogasalad.model.constructable.BoardImage;

/**
 * <p>A basic wrapper around an ImageView object that contains the backend boardImage
 * for use in the backend.</p>
 *
 * @author tmh85
 */
public class BoardImageTile extends ImageView {

  private final BoardImage myBoardImage;

  public BoardImageTile(BoardImage boardImage) {
    super();
    myBoardImage = boardImage;
    bindAttributes();
  }

  private void bindAttributes() {
    this.scaleXProperty().bindBidirectional(myBoardImage.scaleAttribute().valueProperty());
    this.scaleYProperty().bindBidirectional(myBoardImage.scaleAttribute().valueProperty());
    this.rotateProperty().bindBidirectional(myBoardImage.positionAttribute().angleProperty());
    this.xProperty().bindBidirectional(myBoardImage.positionAttribute().xProperty());
    this.yProperty().bindBidirectional(myBoardImage.positionAttribute().yProperty());
    this.imageProperty().addListener(((observable, oldValue, newValue) -> {
      myBoardImage.setImage(newValue.getUrl());
    }));
  }

  public BoardImage getBoardImage() {
    return myBoardImage;
  }
}
