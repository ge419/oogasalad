package oogasalad.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Simple interface that can be used to create an ImageView from a path
 *
 * <p>Assumptions: Assumes that the directory holding the image file is marked as a resource root
 *
 * <p>Dependencies: None
 *
 * <p>Example usage:
 * <pre>{@code
 * ImageView imageView = createImage(50, "view.gameplay/default.jpg", 1);
 * }</pre>
 *
 * <p>Other details: None
 *
 * @author Woonggyu wj61
 */

public interface Imageable {

  /**
   * Creates an ImageView from an ImagePath. Also rescales the image with specified scale
   * parameter.
   *
   * <p>Assumptions: Valid width is given, with colors given as JavaFX Color objects +
   * width of the border of the background is 1.
   *
   * <p>Parameters:
   *
   * @param width    is the width of the Node that the image is to be added to
   * @param imgPath  is the path to the image
   * @param imgScale is the scale parameter to be used to scale the image.
   * @return ImageView of specified size and scale
   */
  default ImageView createImage(double width, String imgPath, double imgScale) {
    ImageView imageView = new ImageView();
    Image img = new Image(imgPath);
    imageView.setImage(img);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(width / imgScale);
    imageView.setSmooth(true);
    imageView.setCache(true);
    return imageView;
  }
}
