package oogasalad.view.tiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Coordinate;
import oogasalad.view.Textable;

public class StreetTile extends StackPane implements Tile, Textable {
  private static final double TEXT_SCALE = 8;

  public StreetTile(int id, Coordinate position, Color color, String name, String price,
      double width, double height) {
    //TODO: delete once we get backend tile
    Map<String, String> textMap = new HashMap<>();
    textMap.put("name", name);
    textMap.put("price", price);

    getChildren().addAll((createBarBox(width, height, color)), createTextBox(textMap, height, width));
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

  @Override
  public VBox createTextBox(Map<String, String> textMap, double height, double width) {
    VBox textBox = new VBox();
    Text streetText = new Text(textMap.get("name"));
    resizeText(streetText, height, TEXT_SCALE, width);
    streetText.setLayoutY(this.getLayoutY());
    Text priceText = new Text(textMap.get("price"));
    resizeText(priceText, height, TEXT_SCALE, width);
    textBox.setMargin(priceText, new Insets(height / 6, 0, 0 , 0));
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
