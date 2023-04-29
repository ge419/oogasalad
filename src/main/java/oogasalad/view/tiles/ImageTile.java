package oogasalad.view.tiles;

import java.util.List;
import com.google.inject.Inject;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import oogasalad.model.attribute.PlayerAttribute;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;

public class ImageTile extends StackPane implements ViewTile, Textable, Imageable, Backgroundable {

  private static final double TEXT_SCALE = 8;
  private static final double MARGIN_SCALE = 10;
  private static final double IMAGE_SCALE = 1.5;
  private static final Color TILE_BACKGROUND = Color.WHITE;
  private static final Color TILE_STROKE_COLOR = Color.BLACK;

  public static final String IMAGE_ATTRIBUTE = "image";
  private final Tile modelTile;

  @Inject
  public ImageTile(Tile BTile) {
    this.setPosition(BTile.getCoordinate());
    this.modelTile = BTile;

    Rectangle tileBackground = createBackground(BTile.getWidth(), BTile.getHeight(), TILE_BACKGROUND, TILE_STROKE_COLOR);
    ImageView tileImage = createImage(BTile.getWidth(),
        StringAttribute.from(BTile.getAttribute(IMAGE_ATTRIBUTE).get()).getValue(), IMAGE_SCALE);

    VBox content = new VBox(BTile.getHeight() / MARGIN_SCALE, tileImage,
        createTextBox(List.of(BTile.getInfo()), BTile.getHeight(), BTile.getHeight()));
    content.setAlignment(Pos.CENTER);
    getChildren().addAll(tileBackground, content);

    //TODO: change this temporary behavior when tile is bought
    //TODO: depend on if attribute is present
    modelTile.getAttribute(BuyTileRule.OWNER_ATTRIBUTE)
        .map(PlayerAttribute::from)
        .map(PlayerAttribute::idProperty)
        .ifPresent(prop -> prop.addListener((observable, oldValue, newValue) ->
            newValue.ifPresentOrElse(
                // Tile is owned
                id -> this.setColor(Color.RED),
                // Tile is not owned
                () -> this.setColor(Color.LIGHTBLUE)
            )));
  }

  @Override
  public VBox createTextBox(List info, double height, double width) {
    VBox textBox = new VBox();
    for (int i = 0; i < info.size(); i++) {
      Text text = new Text(info.get(i).toString());
      resizeText(text, height, TEXT_SCALE, width);
      textBox.getChildren().add(text);
    }
    textBox.setAlignment(Pos.CENTER);
    return textBox;
  }

  public void setColor(Color color) {

  }

  @Override
  public Tile getTile() {
    return this.modelTile;
  }

  @Override
  public void setSize(double width, double height) {
    this.setWidth(width);
    this.setHeight(height);
  }


  @Override
  public Node asNode() {
    return this;
  }

  @Override
  public String getTileId() {
    return this.modelTile.getId();
  }

  public Coordinate getPosition() {
    return null;
  }

  public void setPosition(Coordinate coord) {
    this.setLayoutX(coord.getXCoor());
    this.setLayoutY(coord.getYCoor());
    this.getTransforms().add(new Rotate(coord.getAngle(), Rotate.Z_AXIS));
  }

  public Paint getColor() {
    return null;
  }


}
