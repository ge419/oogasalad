package oogasalad.view.tiles;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.Textable;

public class StreetTile extends StackPane implements ViewTile, Textable {
  private static final double TEXT_SCALE = 8;

  public StreetTile(int id, Coordinate coordinate, Color color, String name, String price,
      double width, double height) {
    //TODO: delete once we get backend tile
    Map<String, String> textMap = new HashMap<>();
    textMap.put("name", name);
    textMap.put("price", price);

    getChildren().addAll((createBarBox(width, height, color)), createTextBox(textMap, height, width));
    setPosition(coordinate);
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
  public void setColor(Color color) {

  }

  @Override
  public Tile getTile() {
    return null;
  }


  @Override
  public String getTileId() {
    return null;
  }

  @Override
  public Coordinate getPosition() {
    return null;
  }

  @Override
  public void setPosition(Coordinate coord) {
    this.setLayoutX(coord.getXCoor());
    this.setLayoutY(coord.getYCoor());
  }

  @Override
  public Paint getColor() {
    return null;
  }

}
