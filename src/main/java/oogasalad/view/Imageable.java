package oogasalad.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Imageable {
  double IMG_SCALE = 1.5;

  default ImageView createImage(double width, String imgPath) {
    ImageView imageView = new ImageView();
    Image img = new Image(imgPath);
    imageView.setImage(img);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(width / IMG_SCALE);
    imageView.setSmooth(true);
    imageView.setCache(true);
    return imageView;
  }
}
