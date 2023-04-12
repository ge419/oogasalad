package oogasalad.view.tiles;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.Textable;

public class StreetTile extends StackPane implements Tile, Textable {
  private static final double TEXT_SCALE = 8;

  public StreetTile(int id, Coordinate position, Color color, String name, String price,
      double width, double height) {
    getChildren().addAll((createBarBox(width, height, color)), createTextBox(name, price, height));
    setPosition(position);
  }

  private Rectangle createBar(double width, double height, Color color) {
    Rectangle bar = new Rectangle();
    bar.setWidth(width);
    bar.setHeight(height);
    bar.setFill(color);
    bar.setStroke(Color.BLACK);
    bar.setStrokeWidth(1);
    return bar;
  }

  private VBox createBarBox(double width, double height, Color color) {
    VBox barBox = new VBox();
    Rectangle topBar = createBar(width, height / 6, color);
    Rectangle bottomBar = createBar(width, 5 * height / 6, Color.WHITE);
    barBox.getChildren().addAll(topBar, bottomBar);
    return barBox;
  }

  private VBox createTextBox(String name, String price, double height) {
    VBox textBox = new VBox();
    Text streetText = new Text(name);
    resizeText(streetText, height, TEXT_SCALE);
    textBox.setMargin(streetText, new Insets(height / 6, 0, height / 3, 0));
    Text priceText = new Text(price);
    resizeText(priceText, height, TEXT_SCALE);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(streetText, priceText);
    return textBox;
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
    this.setLayoutX(coord.getXCoor());
    this.setLayoutY(coord.getYCoor());
  }
}
