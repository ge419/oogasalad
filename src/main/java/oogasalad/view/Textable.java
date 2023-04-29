package oogasalad.view;

import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public interface Textable {

  default void resizeText(Text text, double componentHeight, double scale, double componentWidth) {
    Bounds textBounds = text.getBoundsInLocal();
    double textScale = componentHeight / textBounds.getHeight() / scale;
    text.setWrappingWidth(componentWidth / 1.5);
    text.setTextAlignment(TextAlignment.CENTER);
    text.setScaleX(textScale);
    text.setScaleY(textScale);
  }

  VBox createTextBox(String info, String price, double height, double width);
}
