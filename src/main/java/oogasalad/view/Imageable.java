package oogasalad.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Imageable {

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
