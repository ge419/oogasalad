package oogasalad.view.tiles;

import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;

public class ImageTile extends StackPane implements ViewTile, Textable, Imageable {
  private static final double TEXT_SCALE = 8;
  private static final double MARGIN_SCALE = 10;

  public ImageTile(int id, Coordinate coordinate, String imgPath, Map<String, String> textMap, double width,
      double height) {
    this.setPosition(coordinate);
    //TODO: take this code out once we get backend tile

    Rectangle tileBackground = tileBackground(width, height);
    ImageView tileImage = createImage(width, imgPath);

    VBox content = new VBox(height / MARGIN_SCALE, tileImage, createTextBox(textMap, height, width));
    content.setAlignment(Pos.CENTER);
    getChildren().addAll(tileBackground, content);
  }

  private Rectangle tileBackground(double width, double height) {
    Rectangle background = new Rectangle(width, height);
    background.setFill(Color.WHITE);
    background.setStroke(Color.BLACK);
    background.setStrokeWidth(1);
    return background;
  }

  @Override
  public VBox createTextBox(Map<String, String> textMap, double height, double width) {
    VBox textBox = new VBox();
    for (String key : textMap.keySet()) {
      Text text = new Text(textMap.get(key));
      resizeText(text, height, TEXT_SCALE, width);
      textBox.getChildren().add(text);
    }
    textBox.setAlignment(Pos.CENTER);
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
  public Node asNode() {
    return this;
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
