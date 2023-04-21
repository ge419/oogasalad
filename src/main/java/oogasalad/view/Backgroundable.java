package oogasalad.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public interface Backgroundable {

  default Rectangle createBackground(double width, double height, Color backgroundColor,
      Color strokeColor) {
    Rectangle background = new Rectangle(width, height);
    background.setFill(backgroundColor);
    background.setStroke(strokeColor);
    background.setStrokeWidth(1);
    return background;
  }
}
