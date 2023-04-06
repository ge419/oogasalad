package oogasalad.gameplay_frontend.pieces;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.gameplay_frontend.Movable;

abstract public class GamePiece extends ImageView implements Movable {
  private static double PIECE_SIZE = 100;
  public GamePiece(String imageURL) {
    try {
      Image image = new Image(new FileInputStream(imageURL));
      this.setImage(image);
      this.setFitHeight(PIECE_SIZE);
      this.setFitWidth(PIECE_SIZE);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
