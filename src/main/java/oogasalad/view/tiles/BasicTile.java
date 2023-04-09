package oogasalad.view.tiles;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Renderable;
import oogasalad.view.Coordinate;

public class BasicTile extends Rectangle implements Tile {
  private static final double TILE_WIDTH = 50;

  private final int id;
  private final double[] position;
  private final int[] next;
  private final int[] onLand;
  private final int[] afterTurn;

  public BasicTile(int id, double[] position, int[] next, int[] onLand, int[] afterTurn) {
    super(position[0], position[1], TILE_WIDTH, TILE_WIDTH);
    this.setFill(Color.LIGHTBLUE);
    this.setStroke(Color.BLACK);
    this.id = id;
    this.position = position;
    this.next = next;
    this.onLand = onLand;
    this.afterTurn = afterTurn;
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

  public double[] getPosition() {
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
  public void setPosition(Coordinate coord) {
    this.setX(coord.getXCoor());
    this.setY(coord.getYCoor());
  }

  @Override
  public boolean equals(Object o){
    if (o == null || getClass() != o.getClass()) return false;
    return this.getTileId() == ((BasicTile) o).getTileId();
  }
}
