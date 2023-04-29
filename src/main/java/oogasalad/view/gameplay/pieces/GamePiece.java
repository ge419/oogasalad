package oogasalad.view.gameplay.pieces;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import oogasalad.view.Imageable;
import oogasalad.view.gameplay.Movable;

abstract public class GamePiece extends StackPane implements Movable {

  private static final double PIECE_SIZE = 40;
  private Image image;
  private double xCoor;
  private double yCoor;
  private ImageView imageView;

  public GamePiece(String imageURL) {
      imageView = new ImageView(image);
      imageView.setFitHeight(PIECE_SIZE);
      imageView.setFitWidth(PIECE_SIZE);
      getChildren().add(imageView);
  }

  protected void setxCoor(double value) {
    xCoor = value;
  }

  protected void setyCoor(double value) {
    yCoor = value;
  }

}
