package oogasalad.gameplay_frontend.Tiles;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

public class BasicTile extends Rectangle {
  private static final double TILE_WIDTH = 50;

  private int id;
  private double[] position;
  private int[] next;
  private int[] onLand;
  private int[] afterTurn;

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
}
