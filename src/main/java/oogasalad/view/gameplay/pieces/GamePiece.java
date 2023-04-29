package oogasalad.view.gameplay.pieces;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.view.gameplay.Movable;

abstract public class GamePiece extends ImageView implements Movable {

  private static final double PIECE_SIZE = 40;
  protected Image image;
  private double xCoor;
  private double yCoor;

  public GamePiece(String imageURL) {
    image = new Image(imageURL);
    this.setImage(image);
    this.setFitHeight(PIECE_SIZE);
    this.setFitWidth(PIECE_SIZE);
  }

  protected void setxCoor(double value) {
    xCoor = value;
  }

  protected void setyCoor(double value) {
    yCoor = value;
  }

}
