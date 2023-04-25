package oogasalad.view.gameplay.pieces;

import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;

public class PlayerPiece extends GamePiece {

  private final Piece modelPiece;

  public PlayerPiece(Piece piece) {
    //TODO: image getter for player, create image attribute
    super("data/example/piece_1.png");
    this.modelPiece = piece;

    setOnMouseClicked(event -> {
      //TODO: remove this and implement a button in GameView that passes in a coordinate
      Coordinate coor = new Coordinate(300.0, 300.0, 0);
      moveDirectly(coor);
    });

  }

//  public void changePlayerName(String newName) {
//    modelPlayer.setName(newName);
//  }

  @Override
  public void move(Coordinate[] coorArray) {

  }

  public void moveToTile(Tile tile) {
    modelPiece.setTileId(tile.getId());
    moveDirectly(tile.getCoordinate());
  }

  @Override
  public void moveDirectly(Coordinate coor) {
    this.setLayoutX(coor.getXCoor());
    this.setLayoutY(coor.getYCoor());
  }

}
