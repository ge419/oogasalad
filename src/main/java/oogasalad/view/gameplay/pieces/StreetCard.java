package oogasalad.view.gameplay.pieces;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.Movable;
import oogasalad.view.tiles.StreetTile;

/**
 * This class represents a street card that extends the StreetTile class and implements the Movable interface.
 * A street card is movable and clickable to bring up a popup with additional information about the card.
 *
 * Assumptions:
 * - The BTile argument passed to the constructor is not null.
 *
 * Dependencies:
 * - com.google.inject.Inject
 * - com.google.inject.assistedinject.Assisted
 * - oogasalad.model.constructable.Tile
 * - oogasalad.view.Coordinate
 * - oogasalad.view.gameplay.Movable
 * - oogasalad.view.tiles.StreetTile
 *
 * Example usage:
 * Tile BTile = new Tile();
 * StreetCard streetCard = new StreetCard(BTile);
 * streetCard.setSelected(true);
 * streetCard.move(new Coordinate[]{new Coordinate(0,0), new Coordinate(1,1)});
 *
 * Additional details:
 * - This class is used in the gameplay view to display street cards on the game board.
 * - This class allows for the street card to be selected and moved around the game board.
 * - This class depends on the Tile class to initialize the street card object.
 *
 * @author ajo24
 */
public class StreetCard extends StreetTile implements Movable {

  /**
   * Constructs a new StreetCard object using the given BTile.
   *
   * @param BTile the underlying Tile object representing the street card.
   */
  @Inject
  public StreetCard(@Assisted Tile BTile) {
    super(BTile);
    setMouseTransparent(false);
    this.setOnMouseClicked(this::createCardPopup);
  }

  /**
   * Moves the street card to the specified coordinates on the game board.
   *
   * @param coorArray an array of coordinates representing the path the card will move along.
   */
  @Override
  public void move(Coordinate[] coorArray) {

  }

  /**
   * Sets whether the street card is selected or not. If selected, the card will be raised by 10 pixels.
   *
   * @param selected a boolean value indicating whether the street card is selected or not.
   */
  public void setSelected(boolean selected) {
    if (selected) {
      setTranslateY(getTranslateY() - 10);
    } else {
      setTranslateY(getTranslateY() + 10);
    }
  }

}