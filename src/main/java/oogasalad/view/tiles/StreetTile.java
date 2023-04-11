package oogasalad.view.tiles;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Coordinate;

public class StreetTile extends ImageView implements Tile {
  private int id;
  private Coordinate position;
  private Color color;
  private String name;
  private String price;
  private double tileWidth;
  private double tileHeight;

  public StreetTile(int id, Coordinate position, Color color, String name, String price,
      double width, double height) {
    this.name = name;
    this.color = color;
    this.price = price;
    this.tileWidth = width;
    this.tileHeight = height;
  }

  @Override
  public int getTileId() {
    return 0;
  }

  @Override
  public double[] getPosition() {
    return new double[0];
  }

  @Override
  public int[] getNext() {
    return new int[0];
  }

  @Override
  public int[] getOnLand() {
    return new int[0];
  }

  @Override
  public int[] getAfterTurn() {
    return new int[0];
  }

  @Override
  public void setColor(Color color) {

  }

  @Override
  public void setPosition(Coordinate coord) {

  }
}
