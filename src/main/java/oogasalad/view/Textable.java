package oogasalad.view;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * <p> Simple interface that provides useful methods that scale and resize text
 *
 * <p>Assumptions: Assumes that the text the user tries to resize is in a Text JavaFX object
 *
 * <p>Dependencies: None
 *
 * <p>Example usage:
 * <pre>{@code
 * resizeText(playerName, height, USERNAME_TEXT_SCALE, width);
 * }</pre>
 *
 * <p>Other details: None
 *
 * @author Woonggyu wj61
 */

public interface Textable {

  /**
   * Resize text in a Text JavaFX component to fit within specified height and width with text
   * wrapping and a specified scale
   *
   * <p>Assumptions: Valid component height, width is given and user specifies scale to their own
   * discretion.
   *
   * <p>Parameters:
   *
   * @param text            is the JavaFX text object containing the text to be resized
   * @param componentHeight is the height of JavaFX node that holds the text
   * @param componentWidth  is the width of the JavaFX node that holds the text
   * @param scale           is the scale parameter to be used to resize the text
   */
  default void resizeText(Text text, double componentHeight, double scale, double componentWidth) {
    double WRAPPING_BUFFER = 1.5;
    Bounds textBounds = text.getBoundsInLocal();
    double textScale = componentHeight / textBounds.getHeight() / scale;
    text.setWrappingWidth(componentWidth / WRAPPING_BUFFER);
    text.setTextAlignment(TextAlignment.CENTER);
    text.setScaleX(textScale);
    text.setScaleY(textScale);
  }

  /**
   * Create a textbox with a list of information to be rendered
   *
   * <p>Assumptions: Text is to be ordered vertically; list to be rendered have valid .toString
   * methods
   *
   * <p>Parameters:
   *
   * @param height is the desired height of the textbox being created
   * @param info   is a list of all information that are to be created
   * @param width  is the desired width of the textbox being created
   * @return VBox with all text that is to be rendered
   */
  VBox createTextBox(List info, double height, double width);
}
