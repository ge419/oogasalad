package oogasalad.view.tiles;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.Textable;

public class StreetTile extends StackPane implements ViewTile, Textable {

  private static final double TEXT_SCALE = 8;
  public static final String COLOR_ATTRIBUTE = "color";


  public StreetTile(Tile BTile) {

    getChildren().addAll((createBarBox(BTile.getWidth(), BTile.getHeight(),
            StringAttribute.from(BTile.getAttribute(COLOR_ATTRIBUTE)).getValue())),
        createTextBox(BTile.getInfo(), BTile.getHeight(), BTile.getWidth()));
    setPosition(BTile.getCoordinate());
  }

  private Rectangle createBar(double width, double height, String color) {
    Rectangle bar = new Rectangle();
    bar.setWidth(width);
    bar.setHeight(height);
    bar.setFill(Color.web(color));
    bar.setStroke(Color.BLACK);
    bar.setStrokeWidth(1);
    return bar;
  }

  private VBox createBarBox(double width, double height, String color) {
    VBox barBox = new VBox();
    Rectangle topBar = createBar(width, height / 6, color);
    Rectangle bottomBar = createBar(width, 5 * height / 6, "FFFFFF");
    barBox.getChildren().addAll(topBar, bottomBar);
    return barBox;
  }

  @Override
  public VBox createTextBox(String info, double height, double width) {
    VBox textBox = new VBox();
    String[] infoList = info.split(",");

    Text streetText = new Text(infoList[0]);
    resizeText(streetText, height, TEXT_SCALE, width);
    Bounds streetTextBounds = streetText.getBoundsInLocal();
    streetText.setLayoutY(this.getLayoutY());
    Text priceText = new Text(infoList[1]);
    resizeText(priceText, height, TEXT_SCALE, width);
    textBox.setMargin(priceText, new Insets((height / 4 - streetTextBounds.getMaxY()), 0, 0, 0));
    textBox.setAlignment(Pos.CENTER);
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
