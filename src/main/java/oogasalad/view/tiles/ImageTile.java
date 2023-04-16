package oogasalad.view.tiles;

import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;

public class ImageTile extends StackPane implements ViewTile, Textable, Imageable, Backgroundable {

  private static final double TEXT_SCALE = 8;
  private static final double MARGIN_SCALE = 10;
  public static final String IMAGE_ATTRIBUTE = "image";

  public ImageTile(Tile BTile) {
    this.setPosition(BTile.getCoordinate());

    Rectangle tileBackground = tileBackground(BTile.getWidth(), BTile.getHeight());
    System.out.println(tileBackground.getX());
    ImageView tileImage = createImage(BTile.getWidth(),
        StringAttribute.from(BTile.getAttribute("image")).getValue());

    VBox content = new VBox(BTile.getHeight() / MARGIN_SCALE, tileImage,
        createTextBox(BTile.getInfo(), BTile.getHeight(), BTile.getHeight()));
    content.setAlignment(Pos.CENTER);
    getChildren().addAll(tileBackground, content);
  }

  @Override
  public VBox createTextBox(String info, double height, double width) {
    VBox textBox = new VBox();
    String[] infoList = info.split(",");
    for (int i = 0; i < infoList.length; i++) {
      Text text = new Text(infoList[i]);
//      resizeText(text, height, TEXT_SCALE, width);
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
    System.out.println(this.getLayoutX());
    this.setLayoutY(coord.getYCoor());
  }

  @Override
  public Paint getColor() {
    return null;
  }


}
