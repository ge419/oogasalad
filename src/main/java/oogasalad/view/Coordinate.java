package oogasalad.view;

public class Coordinate {

  private double xCoor;
  private double yCoor;
  public Coordinate(double x, double y) {
    //TODO: add error handling for bounds of board/tiles
    xCoor = x;
    yCoor = y;
  }

  public void setXCoor(int x) {
    xCoor = x;
  }

  public void setYCoor(int y) {
    yCoor = y;
  }

  public double getXCoor() {return xCoor;}

  public double getYCoor() {return yCoor;}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinate that = (Coordinate) o;
    return xCoor == that.getXCoor() &&
        yCoor == that.getYCoor();
  }

  @Override
  public String toString() {
    return "(" + xCoor + "," + yCoor + ")";
  }

}
