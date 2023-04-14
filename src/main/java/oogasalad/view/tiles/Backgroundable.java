package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public interface Backgroundable {
  default Rectangle tileBackground(double width, double height) {
    Rectangle background = new Rectangle(width, height);
    background.setFill(Color.WHITE);
    background.setStroke(Color.BLACK);
    background.setStrokeWidth(1);
    return background;
  }
}
