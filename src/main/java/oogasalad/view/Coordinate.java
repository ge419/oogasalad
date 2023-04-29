package oogasalad.view;

public class Coordinate {

  private double xCoor;
  private double yCoor;
  private double tileAngle;

  public Coordinate(double x, double y) {
    this(x, y, 0);
  }

  public Coordinate(double x, double y, double angle) {
    //TODO: add error handling for bounds of board/tiles
    xCoor = x;
    yCoor = y;
    tileAngle = angle;
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

  public double getAngle() {
    return tileAngle;
  }

  public void setAngle(double angle) {
    tileAngle = angle;
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
        yCoor == that.getYCoor() && tileAngle == that.tileAngle;
  }

  @Override
  public String toString() {
    return "(" + xCoor + "," + yCoor + "), angle is " + tileAngle;
  }

}
