package oogasalad.controller.builderevents;

import javafx.scene.Node;

/**
 * TrailMakers will create some sort of trail between any given nodes, and then store all created
 * trails inside itself. It will also visually place the trails inside the desired pane.
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
   * Flips the current enable state of the guidelines
   */
  void toggleEnable();

}
