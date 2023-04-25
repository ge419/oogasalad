package oogasalad.view.gameplay.pieces;

import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.util.Util;
import oogasalad.view.Coordinate;

public class PlayerPiece extends GamePiece {

  private final Piece modelPiece;

  public PlayerPiece(Piece modelPiece) {
    //TODO: image getter for player, create image attribute
    super("data/example/piece_1.png");
    this.modelPiece = modelPiece;
    //TODO: BIND PLAYER piece position to MODEL player
    //make sure it refreshes per tile change
//    xProperty().bind(modelPlayer.getX());
//    yProperty().bind(modelPlayer.getY());

    Util.initializeAndListen(modelPiece.tileProperty(),
        optionalTile -> optionalTile.ifPresent(this::moveToTile));
  }

  @Override
  public void move(Coordinate[] coorArray) {
    //change player tile position attribute?
  }

  public void moveToTile(Tile tile) {
    modelPiece.setTile(tile);
    moveDirectly(tile.getCoordinate());
  }

  @Override
  public void moveDirectly(Coordinate coor) {
    this.setLayoutX(coor.getXCoor());
    this.setLayoutY(coor.getYCoor());
  }

}
