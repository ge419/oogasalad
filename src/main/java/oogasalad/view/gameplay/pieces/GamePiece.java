package oogasalad.view.gameplay.pieces;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.view.gameplay.Movable;

abstract public class GamePiece extends ImageView implements Movable {
  private static double PIECE_SIZE = 20;
  private double xCoor;
  private double yCoor;
  protected Image image;
  public GamePiece(String imageURL) {

    try {
      image = new Image(new FileInputStream(imageURL));
      this.setImage(image);
      this.setFitHeight(PIECE_SIZE);
      this.setFitWidth(PIECE_SIZE);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void setxCoor(double value) {
    xCoor = value;
  }

  protected void setyCoor(double value) {
    yCoor = value;
  }

}
