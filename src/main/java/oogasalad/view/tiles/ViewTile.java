package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import oogasalad.view.Coordinate;

public interface ViewTile {

  /**
   * Returns the integer id for a given tile.
   * @return id
   */
  public String getTileId();

  /**
   * <p>Returns the position of the tile as a double array.</p>
   * <p>Index 0 is the X position, index 1 is the Y position.</p>
   * <p>Note that 0,0 is the top left corner of the tile</p>
   * @return double of size 2 with x and y position
   */
  public Double[] getPosition();

  /**
   * <p>Sets the position of the tile.</p>
   * <p>Note that the top left corner is 0,0.</p>
   * @param coord Coordinate that you want the tile to go to.
   */
  public void setPosition(Coordinate coord);

  /**
   * Returns the current color of a tile.
   * @return color of tile
   */
  public Paint getColor();

  /**
   * Set the color of a tile using a color object.
   * @param color color you want the tile to be.
   */
  public void setColor(Color color);

}
