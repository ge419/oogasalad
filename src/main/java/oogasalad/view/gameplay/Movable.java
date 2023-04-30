package oogasalad.view.gameplay;


import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.pieces.GamePiece;

public interface Movable {

  void move(Coordinate[] coorArray);

  default void moveDirectly(Coordinate coor, GamePiece piece) {
    piece.setLayoutX(coor.getXCoor());
    piece.setLayoutY(coor.getYCoor());
  }
}