package oogasalad.gameplay_frontend.Tiles;

import javafx.scene.image.ImageView;
import oogasalad.gameplay_frontend.Settable;

public abstract class Tile extends ImageView implements Settable {
  private double xCoordinate;
  private double yCoordinate;
}
