package oogasalad.view.tiles;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;

public class BasicTile extends Rectangle implements ViewTile {
  private static final double TILE_WIDTH = 50;

  private final int id;
  private Double[] position;
  private int[] next;
  private int[] onLand;
  private int[] afterTurn;
  private boolean owned;

  public BasicTile(Tile tile, int id) {
    super(PositionAttribute.from(tile.getAttribute("position")).getX(), PositionAttribute.from(tile.getAttribute("position")).getY(), TILE_WIDTH, TILE_WIDTH);
    this.setFill(Color.LIGHTBLUE);
    this.setStroke(Color.BLACK);
    this.id = id;
//    this.id = IntAttribute.from(tile.getAttribute("id")).getValue();
    this.position = new Double[]{PositionAttribute.from(tile.getAttribute("position")).getX(), PositionAttribute.from(tile.getAttribute("position")).getY()};
//    this.next = new int[]{TileAttribute.from(tile.getAttribute("nextTile")).getValue()};
//    this.onLand = new int[]{TileAttribute.from(tile.getAttribute("onLand")).getValue()};
//    this.afterTurn = new int[]{TileAttribute.from(tile.getAttribute("afterTurn")).getValue()};
  }

  public BasicTile(int id, Coordinate position) {
    super(position.getXCoor(), position.getYCoor(), TILE_WIDTH, TILE_WIDTH);
    this.setFill(Color.LIGHTBLUE);
    this.setStroke(Color.BLACK);
    this.id = id;
  }

  public int getTileId() {
    return id;
  }

  public Double[] getPosition() {
    return position;
  }

  public int[] getNext() {
    return next;
  }

  public int[] getOnLand() {
    return onLand;
  }

  public int[] getAfterTurn() {
    return afterTurn;
  }

  @Override
  public void setColor(Color color) {
    this.setFill(color);
  }

  @Override
  public void setOwned(boolean owned) {
    this.owned = owned;
    if (owned) {
      setColor(Color.RED);
    } else {
      setColor(Color.LIGHTBLUE);
    }
  }

  @Override
  public boolean isOwned() {
    return owned;
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
