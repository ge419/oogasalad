package oogasalad.view.gameplay.pieces;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.Nodeable;
import oogasalad.view.gameplay.Movable;
import oogasalad.view.tiles.ImageTile;

/**
 * This class represents an ImageCard, a type of ImageTile that can be moved and clicked on to
 * create a popup. It extends the ImageTile class and implements the Movable and Nodeable
 * interfaces.
 * <p>
 * The ImageCard class assumes that it is being used in a game where the user can interact with
 * cards by clicking on them. It depends on the Tile and Coordinate classes from the model package,
 * and on the Nodeable and Movable interfaces from the view package.
 * <p>
 * An example of how to use the ImageCard class is as follows:
 * <p>
 * Tile BTile = new Tile(); // Create a new Tile object ImageCard imageCard = new ImageCard(BTile);
 * // Create a new ImageCard object with the Tile object as a parameter Coordinate[] coorArray = new
 * Coordinate[] { new Coordinate(0, 0), new Coordinate(1, 0) }; // Create a Coordinate array
 * imageCard.move(coorArray); // Move the ImageCard object to the specified coordinates
 * imageCard.setSelected(true); // Set the ImageCard object as selected, causing it to move up by 10
 * pixels
 * <p>
 * Users should note that the setSelected() method can only move the ImageCard object up or down by
 * 10 pixels, and the move() method does not have any implementation.
 */
public class ImageCard extends ImageTile implements Movable, Nodeable {

  /**
   * Constructs a new ImageCard object with the given Tile object as a parameter.
   *
   * @param BTile the Tile object to be used in creating the ImageCard object
   */
  @Inject
  public ImageCard(@Assisted Tile BTile) {
    super(BTile);
    setMouseTransparent(false);
    this.setOnMouseClicked(this::createCardPopup);
  }


  /**
   * This method does not have any implementation in the ImageCard class.
   *
   * @param coorArray an array of Coordinates to move the ImageCard object to
   */
  @Override
  public void move(Coordinate[] coorArray) {

  }


  /**
   * Sets the ImageCard object as selected, causing it to move up by 10 pixels if selected is true,
   * and down by 10 pixels if selected is false.
   *
   * @param selected a boolean value indicating whether the ImageCard object should be selected or
   *                 deselected
   */
  public void setSelected(boolean selected) {
    if (selected) {
      setTranslateY(getTranslateY() - 10);
    } else {
      setTranslateY(getTranslateY() + 10);
    }
  }

}