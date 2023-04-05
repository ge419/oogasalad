package oogasalad.gameplay_frontend;

import java.util.List;

public interface Settable {
  void set(double xCoordinate, double yCoordinate);

  List<Double> getPosition();
}
