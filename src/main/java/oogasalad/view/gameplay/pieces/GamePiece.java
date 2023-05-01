package oogasalad.view.gameplay.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import oogasalad.view.gameplay.Movable;

/**
 * This class represents a game piece that can be moved on the game board. It extends the JavaFX StackPane and implements the Movable interface.
 *
 * This class should be subclassed by specific game pieces that define their unique movement behaviors.
 *
 * Assumptions: The image at the provided imageURL is valid and can be loaded by the JavaFX Image class.
 *
 * Dependencies: This class depends on the JavaFX Image and ImageView classes, as well as the oogasalad.view.gameplay.Movable interface.
 *
 * Example usage:
 * GamePiece piece = new SpecificGamePiece("image.png");
 * gameBoard.add(piece, x, y);
 *
 * @author ajo24
 */
abstract public class GamePiece extends StackPane implements Movable {

  private static final double PIECE_SIZE = 40;

  /**
   * Constructs a GamePiece with the specified image file at imageURL.
   *
   * @param imageURL the URL of the image file to use as the game piece's visual representation
   */
  public GamePiece(String imageURL) {
    Image image = new Image(imageURL);
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(PIECE_SIZE);
    imageView.setFitWidth(PIECE_SIZE);
    getChildren().add(imageView);
  }
}