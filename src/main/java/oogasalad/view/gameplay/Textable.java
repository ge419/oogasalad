package oogasalad.view.gameplay;

import javafx.geometry.Bounds;
import javafx.scene.text.Text;

public interface Textable {

  default void resizeText(Text text, double componentHeight, double scale) {
    //TODO: resize text in the case that it doesn't fit in one line. possibly using space delimiting
    Bounds textBounds = text.getBoundsInLocal();
    double textScale = componentHeight / textBounds.getHeight() / scale;
    text.setScaleX(textScale);
    text.setScaleY(textScale);
  }

}
