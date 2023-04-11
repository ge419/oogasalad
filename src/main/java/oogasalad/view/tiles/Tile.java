package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import oogasalad.view.Coordinate;

public interface Tile {
  public int getTileId();

  public double[] getPosition();
  public void setPosition(Coordinate coord);

  public Paint getColor();
  public void setColor(Color color);

}
