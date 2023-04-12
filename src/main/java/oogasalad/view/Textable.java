package oogasalad.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public interface Textable {

  default void resizeText(Text text, double componentHeight, double scale) {
    //TODO: resize text in the case that it doesn't fit in one line. possibly using space delimiting
    Bounds textBounds = text.getBoundsInLocal();
    double textScale = componentHeight / textBounds.getHeight() / scale;
    text.setScaleX(textScale);
    text.setScaleY(textScale);
  }

  //TODO: once we get backend tile, change it to using that instead of textMap
  VBox createTextBox(Map<String, String> textMap, double height);
}
