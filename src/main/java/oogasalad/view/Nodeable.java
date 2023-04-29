package oogasalad.view;

import javafx.scene.Node;

/**
 * <p>Simple interface that requires an object to be returnable as a node.</p>
 *
 * @author tmh85
 */
public interface Nodeable {

  /**
   * <p>Returns the node that encompasses this tile.
   * (So if this tile extends Rectangle, it will return the Rectangle object.)</p>
   * <p>For tiles that extend a node, this might seem a little redundant, but this is so that other
   * objects can just use ViewTiles to manipulate <em>any</em> kind of tile visually.</p>
   *
   * @return Node
   */
  Node asNode();

}
