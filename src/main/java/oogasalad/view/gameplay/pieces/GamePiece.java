package oogasalad.view.gameplay.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import oogasalad.view.gameplay.Movable;

abstract public class GamePiece extends StackPane implements Movable {

  private static final double PIECE_SIZE = 40;
  private final Image image;
  private double xCoor;
  private double yCoor;
  private final ImageView imageView;

  public GamePiece(String imageURL) {
    image = new Image(imageURL);
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
