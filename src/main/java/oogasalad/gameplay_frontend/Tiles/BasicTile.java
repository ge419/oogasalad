package oogasalad.gameplay_frontend.Tiles;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.gameplay_frontend.Settable;

public class BasicTile extends ImageView implements Settable {
  private int id;
  private List<Double> position;
  private List<Integer> next;
  private List<Integer> onLand;
  private List<Integer> afterTurn;
  public BasicTile(double x, double y) {
    position = List.of(x, y);
  }
  @Override
  public void set(double xCoordinate, double yCoordinate) {
    this.setX(xCoordinate);
    this.setY(yCoordinate);
  }

  @Override
  public List<Double> getPosition() {
    return this.position;
  }

}
