package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.actions.BuyAction;
import oogasalad.view.Coordinate;

public class BasicTile extends Rectangle implements ViewTile {
  private final Tile modelTile;

  public BasicTile(Tile tile) {
    super(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
    this.modelTile = tile;
    this.setFill(Color.LIGHTBLUE);
    this.setStroke(Color.BLACK);

    BooleanAttribute ownedAttribute =
        BooleanAttribute.from(modelTile.getAttribute(BuyAction.OWNED_ATTRIBUTE));
    ownedAttribute.valueProperty().addListener(((observable, oldValue, isOwned) -> {
      if (Boolean.TRUE.equals(isOwned)) {
        this.setFill(Color.RED);
      } else {
        this.setFill(Color.LIGHTBLUE);
      }
    }));
  }

  public Tile getTile() {
    return this.modelTile;
  }

  public String getTileId() {
    return this.modelTile.getId();
  }

  public Coordinate getPosition() {
    return modelTile.getCoordinate();
  }
  @Override
  public void setColor(Color color) {
    this.setFill(color);
  }

  @Override
  public void setPosition(Coordinate coord) {
    this.setX(coord.getXCoor());
    this.setY(coord.getYCoor());
  }

  public Paint getColor() {
    return this.getFill();
  }

  @Override
  public boolean equals(Object o){
    if (o == null || getClass() != o.getClass()) return false;
    return this.getTileId() == ((BasicTile) o).getTileId();
  }
}
