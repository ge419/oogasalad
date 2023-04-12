package oogasalad.view.tiles;

import java.util.HashMap;
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
import oogasalad.view.Textable;

public class ImageTile extends StackPane implements Tile, Textable {
  private static final double TEXT_SCALE = 8;
  private static final double IMG_SCALE = 1.5;
  private static final double MARGIN_SCALE = 10;

  public ImageTile(int id, Coordinate coordinate, String imgPath, String text, double width,
      double height) {
    this.setPosition(coordinate);
    //TODO: take this code out once we get backend tile
    Map<String, String> textMap = new HashMap<>();
    textMap.put("text", text);


    Rectangle tileBackground = tileBackground(width, height);
    ImageView tileImage = tileImage(width, imgPath);

    VBox content = new VBox(height / MARGIN_SCALE, tileImage, createTextBox(textMap, height));
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

  private ImageView tileImage(double width, String imgPath) {
    ImageView imageView = new ImageView();
    Image policeImg = new Image(imgPath);
    imageView.setImage(policeImg);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(width / IMG_SCALE);
    imageView.setSmooth(true);
    imageView.setCache(true);
    return imageView;
  }

  @Override
  public VBox createTextBox(Map<String, String> textMap, double height) {
    VBox textBox = new VBox();
    Text tileText = new Text(textMap.get("text"));
    resizeText(tileText, height, TEXT_SCALE);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(tileText);
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
