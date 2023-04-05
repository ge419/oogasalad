package oogasalad.gameplay_frontend.Tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicTile extends Tile{

  @Override
  public void set(double xCoordinate, double yCoordinate) {
    this.setX(xCoordinate);
    this.setY(yCoordinate);
  }

}
