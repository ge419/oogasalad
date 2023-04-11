package oogasalad.view.tiles;

import java.util.Stack;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.Coordinate;

public class StreetTile extends StackPane implements Tile {
  private static final double STREET_SCALE = 1.3;
  private static final double PRICE_SCALE = 3;

  private int id;
  private Coordinate position;
  private Color color;
  private String name;
  private String price;
  private double tileWidth;
  private double tileHeight;

  public StreetTile(int id, Coordinate position, Color color, String name, String price,
      double width, double height) {
    this.setPrefSize(width, height);
    this.color = color;
    this.name = name;
    this.price = price;
    this.tileWidth = width;
    this.tileHeight = height;
    this.position = position;
    createTile();
    setPosition(position);
  }

  private void createTile() {
    VBox tileBox = new VBox();
    Rectangle topBar = createBar(tileWidth, tileHeight / 6, this.color);
    Rectangle bottomBar = createBar(tileWidth, 5 * tileHeight / 6, Color.WHITE);
    tileBox.getChildren().addAll(topBar, bottomBar);

    VBox textBox = new VBox();
    Text streetText = new Text(this.name);
    resizeText(streetText, STREET_SCALE);
    textBox.setMargin(streetText, new Insets(20, 0, 50, 0));
    Text priceText = new Text(this.price);
    resizeText(priceText, PRICE_SCALE);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(streetText, priceText);

    getChildren().addAll(tileBox, textBox);
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

  private void resizeText(Text text, double scale) {
    Bounds textBounds = text.getBoundsInLocal();
    double textScale = this.tileWidth / textBounds.getWidth() / scale;
    text.setScaleX(textScale);
    text.setScaleY(textScale);
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
