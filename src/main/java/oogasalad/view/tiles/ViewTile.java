package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import oogasalad.view.Coordinate;

public interface ViewTile {
  public int getTileId();
  public Double[] getPosition();
  public int[] getNext();
  public void setColor(Color color);
  public void setPosition(Coordinate coord);

  void setOwned(boolean owned);
  boolean isOwned();

  void setId(String tiles);
}
