package oogasalad.view.gameplay.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.util.Util;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.gameplay.Movable;

public class PlayerPiece extends GamePiece {

  private final Piece modelPiece;

  public PlayerPiece(Piece BPiece) {
    super(BPiece.getImage());
    this.modelPiece = BPiece;
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
