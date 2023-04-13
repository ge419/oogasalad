package oogasalad.view.gameplay;


import oogasalad.view.Coordinate;

public interface Movable {

  void move(Coordinate[] coorArray);

  void moveDirectly(Coordinate coor);
}
