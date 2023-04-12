package oogasalad.view.tiles;

import java.awt.Label;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;

public class ImageTile extends StackPane implements Tile, Textable, Imageable {
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
