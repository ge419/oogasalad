package oogasalad.view.gameplay.pieces;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.view.Coordinate;
import oogasalad.view.Holdable;

public class Cards extends GamePiece {

  public Cards (String imageURL) {
    super(imageURL);
    BorderPane cardBack = new BorderPane();
    cardBack.setCenter(this);
  }


  @Override
  public void move(Coordinate[] coorArray) {

  }

  public void setSelected(boolean selected) {
    if (selected) {
      setTranslateY(getTranslateY() - 10);
    } else {
      setTranslateY(getTranslateY() + 10);
    }
  }

  public double getMaxHeight() {
    return getBoundsInParent().getHeight();
  }

  public double getMaxWidth() {
    return getBoundsInParent().getWidth();
  }

}
