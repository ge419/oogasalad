package oogasalad.controller.builderevents;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * <p>TrailMakers will create some sort of trail between any given nodes, and then store all
 * created trails inside itself. </p>
 * <p>It will also visually place the trails inside the desired pane.</p>
 * <p>Trails can also be re-enabled and disabled based on a toggle method, toggleEnable.</p>
 *
 * @author tmh85
 */
public interface TrailMakerAPI {

  /**
   * <p>Creates a trail between two given nodes.</p>
   * <p>A trail ID must also be provided so that you can access this trail later.</p>
   *
   * @param entry1  first node
   * @param entry2  second node
   * @param trailID desired trail ID
   */
  void createTrailBetween(Node entry1, Node entry2, String trailID);

  /**
   * Removes the trail corresponding to a given trail ID from the parent pane and the trailmaker.
   *
   * @param trailID desired trail
   */
  void removeTrail(String trailID);

  /**
   * <p>Removes all trails corresponding to a given node from the parent pane.</p>
   *
   * @param node Node whose trail you would like to remove
   */
  void removeTrail(Node node);

  /**
   * <p>Convenience method that combines removeTrail and getTrailID.</p>
   *
   * @param entry1 originator node
   * @param entry2 ending node
   * @see TrailMakerAPI#removeTrail(String)
   */
  default void removeTrail(Node entry1, Node entry2) {
    removeTrail(getTrailID(entry1, entry2));
  }

  /**
   * Sets the opacity of a given trail.
   *
   * @param trailID given trail ID
   * @param opacity desired opacity from 0 to 1
   */
  void setOpacity(String trailID, double opacity);

  /**
   * Gets the opacity of a desired trail.
   *
   * @param trailID given trail ID
   * @return opacity (a double from 0 to 1)
   */
  double getOpacity(String trailID);

  /**
   * <p>Flips the current enable state of the guidelines</p>
   * <p>If active, trails are visible. If not, they are not.</p>
   */
  void toggleEnable();

  /**
   * <p>Get the held String ID for a trail given the two nodes that created it.</p>
   * <p>Note that this is <em>order dependent</em>.</p>
   * <p>If the trail cannot be found, null will be returned.</p>
   *
   * @param entry1 first node
   * @param entry2 second node
   * @return string ID
   */
  String getTrailID(Node entry1, Node entry2);

  /**
   * Sets the color of a specific trail
   *
   * @param trailID ID of the trail you want to set the color of.
   * @param color   Color that you wish to set the trail to.
   */
  void setColor(String trailID, Color color);

  /**
   * Sets the color of all the trails currently created.
   *
   * @param color color you wish to set all trails to
   */
  void setAllColors(Color color);

  /**
   * Gets the color of a specific trail
   *
   * @param trailID id of the trail you wish to get the color of
   * @return current color of the trail
   */
  Paint getColor(String trailID);

}
