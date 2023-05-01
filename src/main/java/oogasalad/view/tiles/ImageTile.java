package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.List;
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

/**
 * <p> Tile type that includes an image and text
 *
 * <p>Assumptions: Assumes that the image goes on top with text on bottom
 *
 * <p>Dependencies: ViewTile, Textable, Imageable, Backgroundable interface, Tile object
 *
 * @author Woonggyu wj61
 */

public class ImageTile extends StackPane implements ViewTile, Textable, Imageable, Backgroundable {

  private static final double TEXT_SCALE = 8;
  private static final double MARGIN_SCALE = 10;
  private static final double IMAGE_SCALE = 1.5;
  private static final Color TILE_BACKGROUND = Color.WHITE;
  private static final Color TILE_STROKE_COLOR = Color.BLACK;

  public static final String IMAGE_ATTRIBUTE = "image";
  private final Tile modelTile;

  @Inject
  public ImageTile(@Assisted Tile BTile) {
    this.modelTile = BTile;
    BTile.addSchema("ImageTile");

    Rectangle tileBackground = createBackground(BTile.getWidth(), BTile.getHeight(),
        TILE_BACKGROUND, TILE_STROKE_COLOR);
    ImageView tileImage = createImage(BTile.getWidth(),
        StringAttribute.from(BTile.getAttribute(IMAGE_ATTRIBUTE).get()).getValue(), IMAGE_SCALE);

    VBox content = new VBox(BTile.getHeight() / MARGIN_SCALE, tileImage,
        createTextBox(List.of(BTile.getInfo()), BTile.getHeight(), BTile.getHeight()));
    content.setAlignment(Pos.CENTER);
    getChildren().addAll(tileBackground, content);
  }

  /**
   * @see Textable
   */
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

  /**
   * @return backend tile associated with this frontend tile
   */
  @Override
  public Tile getTile() {
    return this.modelTile;
  }

  /**
   * set the size of this frontend tile
   */
  @Override
  public void setSize(double width, double height) {
    this.setWidth(width);
    this.setHeight(height);
  }

  /**
   * @return return this frontend tile
   */
  @Override
  public Node asNode() {
    return this;
  }

  /**
   * @return the associated backend tile's ID
   */
  @Override
  public String getTileId() {
    return this.modelTile.getId();
  }

}
