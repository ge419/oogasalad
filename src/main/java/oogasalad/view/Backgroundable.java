package oogasalad.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Simple interface that can be used to create a backgrounds for JavaFX objects.
 *
 * <p>Assumptions: Assumes that the background is a rectangle.
 *
 * <p>Dependencies: None
 *
 * <p>Example usage:
 * <pre>{@code
 * Rectangle background = createBackground(50, 50, Color.WHITE, Color.BLACK);
 * }</pre>
 *
 * <p>Other details: None
 *
 * @author Woonggyu wj61
 */

public interface Backgroundable {

  double STROKE_WIDTH = 1;

  /**
   * Creates a background that can be added to numerous JavaFX objects
   *
   * <p>Assumptions: Valid width, height are given, with colors given as JavaFX Color objects +
   * width of the border of the background is 1.
   *
   * <p>Parameters:
   *
   * @param width           is the width of the background
   * @param height          is the height of the background
   * @param backgroundColor is the color of the background
   * @param strokeColor     is the color of the border of the background
   *
   * @return Background as rectangle
   */
  default Rectangle createBackground(double width, double height, Color backgroundColor,
      Color strokeColor) {
    Rectangle background = new Rectangle(width, height);
    background.setFill(backgroundColor);
    background.setStroke(strokeColor);
    background.setStrokeWidth(STROKE_WIDTH);
    return background;
  }
}
