package oogasalad.gameplay_frontend;

public class Coordinate {

  private int xCoor;
  private int yCoor;
  public Coordinate(int x, int y) {
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

  public int getXCoor() {return xCoor;}

  public int getYCoor() {return yCoor;}

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
