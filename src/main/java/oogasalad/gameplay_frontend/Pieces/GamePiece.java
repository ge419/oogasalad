package oogasalad.gameplay_frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract public class GamePiece extends ImageView {
  public GamePiece(String imageURL) {
    setImage(new Image(imageURL));
  }


}
