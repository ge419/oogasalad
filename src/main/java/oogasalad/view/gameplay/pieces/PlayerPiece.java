package oogasalad.view.gameplay.pieces;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
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
    Util.initializeAndListen(modelPiece.tileProperty(),
        optionalTile -> optionalTile.ifPresent(this::moveToTile));
  }

  @Override
  public void move(Coordinate[] coorArray) {
    //change player tile position attribute?
  }

  public void moveToTile(Tile tile) {
    modelPiece.setTile(tile);
    System.out.println(tile.getCoordinate().toString());
    moveDirectly(tile.getCoordinate(), this);
  }

  public Piece getModelPiece() {
    return modelPiece;
  }
}
