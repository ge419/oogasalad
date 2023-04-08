package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import oogasalad.view.Coordinate;

public interface Tile {
  public int getTileId();

  public double[] getPosition();

  public int[] getNext();

  public int[] getOnLand();

  public int[] getAfterTurn();
  public void setColor(Color color);
  public void setPosition(Coordinate coord);
}
