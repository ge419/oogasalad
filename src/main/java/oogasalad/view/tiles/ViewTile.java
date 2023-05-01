package oogasalad.view.tiles;

import javafx.scene.input.MouseEvent;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Nodeable;
import oogasalad.view.gameplay.popup.CardDisplayPopup;

/**
 * <p>ViewTile interface composes the methods that are required for a ViewTile to function.</p>
 *
 * @author tmh85
 */
public interface ViewTile extends Nodeable {

  /**
   * Returns the integer id for a given tile.
   *
   * @return id
   */
  String getTileId();

  /**
   * <p>Returns the position of the tile as a double array.</p>
   * <p>Index 0 is the X position, index 1 is the Y position.</p>
   * <p>Note that 0,0 is the top left corner of the tile</p>
   *
   * @return double of size 2 with x and y position
   */
//  @Deprecated
//  Coordinate getPosition();

  /**
   * <p>Sets the position of the tile.</p>
   * <p>Note that the top left corner is 0,0.</p>
   *
   * @param coord Coordinate that you want the tile to go to.
   */
//  @Deprecated
//  void setPosition(Coordinate coord);

  /**
   * Returns the current color of a tile.
   *
   * @return color of tile
   */
//  @Deprecated
//  Paint getColor();

  /**
   * Set the color of a tile using a color object.
   *
   * @param color color you want the tile to be.
   */
//  @Deprecated
//  void setColor(Color color);

  /**
   * Returns the backend tile contained within the frontend tile.
   *
   * @return backend tile
   */
  Tile getTile();

  /**
   * Sets the ID for a given tile.
   * @param tiles
   */
//  @Deprecated
//  void setId(String tiles);

  /**
   * Sets the width and height of the given ViewTile
   *
   * @param width  width as a double
   * @param height height as a double
   */
  void setSize(double width, double height);

  default void createCardPopup(MouseEvent mouseEvent) {
    String title = StringAttribute.from(this.getTile().getAttribute("info").get()).getValue();
    String description = StringAttribute.from(this.getTile().getAttribute("description").get())
        .getValue();
    System.out.println(title + " " + description);
    CardDisplayPopup cardPopup = new CardDisplayPopup(title, description);
    cardPopup.showCard();
  }
}
