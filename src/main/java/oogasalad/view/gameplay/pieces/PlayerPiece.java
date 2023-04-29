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

  private final ObjectProperty<Piece> modelPiece;
  private final ObjectProperty<Tile> position;

  public PlayerPiece(Piece BPiece) {
    super(BPiece.getImage());
    this.modelPiece = new SimpleObjectProperty<>(BPiece);
    position = new SimpleObjectProperty<>();
    //TODO: BIND PLAYER piece position to MODEL piece
    //make sure it refreshes per tile change
//    xProperty().bind(modelPlayer.getX());
//    yProperty().bind(modelPlayer.getY());

    Util.initializeAndListen(modelPiece.get().tileProperty(),
        optionalTile -> optionalTile.ifPresent(this::moveToTile));
  }

  public ObjectProperty<Tile> positionProperty() {
    return this.position;
  }

  @Override
  public void move(Coordinate[] coorArray) {
    //change player tile position attribute?
  }

  public void moveToTile(Tile tile) {
    modelPiece.get().setTile(tile);
    Bindings.bindBidirectional(this.positionProperty(), this.modelPiece.get().concreteTileProperty());

    moveDirectly(tile.getCoordinate());
  }

  @Override
  public void moveDirectly(Coordinate coor) {
    this.setLayoutX(coor.getXCoor());
    this.setLayoutY(coor.getYCoor());
  }

}
