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

    Rectangle topBar = new Rectangle(tileWidth, tileHeight / 6);
    topBar.setFill(this.color);
    topBar.setStroke(Color.BLACK);
    topBar.setStrokeWidth(1);
    tileBox.getChildren().add(topBar);

    Rectangle bottomBar = new Rectangle(tileWidth, 5 * tileHeight / 6);
    bottomBar.setFill(Color.WHITE);
    bottomBar.setStroke(Color.BLACK);
    bottomBar.setStrokeWidth(1);
    tileBox.getChildren().add(bottomBar);

    VBox textBox = new VBox();
    Text streetText = new Text(this.name);
    Bounds streetTextBounds = streetText.getBoundsInLocal();
    double streetTextScale = this.tileWidth / streetTextBounds.getWidth() / 1.3;
    streetText.setScaleX(streetTextScale);
    streetText.setScaleY(streetTextScale);
    textBox.setMargin(streetText, new Insets(20, 0, 50, 0));

    Text priceText = new Text(this.price);
    Bounds priceTextBounds = priceText.getBoundsInLocal();

    double priceTextScale = this.tileWidth / priceTextBounds.getWidth() / 3;
    priceText.setScaleX(priceTextScale);
    priceText.setScaleY(priceTextScale);
    textBox.setAlignment(Pos.CENTER);

    textBox.getChildren().addAll(streetText, priceText);

    getChildren().addAll(tileBox, textBox);

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
