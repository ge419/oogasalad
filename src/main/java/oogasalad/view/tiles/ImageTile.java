package oogasalad.view.tiles;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.Textable;

public class ImageTile extends StackPane implements Tile, Textable {

  public ImageTile(int id, Coordinate coordinate, String imgPath, String text, double width,
      double height) {
    this.setLayoutX(coordinate.getXCoor());
    this.setLayoutY(coordinate.getYCoor());
    Rectangle tileBackground = tileBackground(width, height);
    ImageView tileImage = tileImage(width, imgPath);
    Text tileText = new Text(text);
    resizeText(tileText, height, 8);
    VBox content = new VBox(height / 10, tileImage, tileText);
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
    imageView.setFitWidth(width / 1.5);
    imageView.setSmooth(true);
    imageView.setCache(true);
    return imageView;
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
