package oogasalad.view;

public class Coordinate {

  private double xCoor;
  private double yCoor;

  public Coordinate(double x, double y) {
    //TODO: add error handling for bounds of board/tiles
    xCoor = x;
    yCoor = y;
  }

  public double getXCoor() {
    return xCoor;
  }

  public void setXCoor(double x) {
    xCoor = x;
  }

  public double getYCoor() {
    return yCoor;
  }

  public void setYCoor(double y) {
    yCoor = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinate that = (Coordinate) o;
    return xCoor == that.getXCoor() &&
        yCoor == that.getYCoor();
  }

  @Override
  public String toString() {
    return "(" + xCoor + "," + yCoor + ")";
  }

}
