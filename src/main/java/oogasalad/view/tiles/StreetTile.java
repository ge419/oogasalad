package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import oogasalad.view.Textable;

/**
 * <p> Tile type that includes a name of tile and price of tile
 *
 * <p>Assumptions: Assumes that the tile is shaped like a standard Monopoly street tile
 *
 * <p>Dependencies: ViewTile, Textable, Backgroundable interface, Tile object
 *
 * @author Woonggyu wj61
 */

public class StreetTile extends StackPane implements ViewTile, Textable, Backgroundable {

  private static final double TEXT_SCALE = 8;
  public static final String COLOR_ATTRIBUTE = "color";
  private final Tile modelTile;

  @Inject
  public StreetTile(@Assisted Tile BTile) {
    this.modelTile = BTile;

    getChildren().addAll((createBarBox(BTile.getWidth(), BTile.getHeight(),
            StringAttribute.from(BTile.getAttribute(COLOR_ATTRIBUTE).get()).getValue())),
        createTextBox(List.of(BTile.getInfo(), BTile.getPrice()), BTile.getHeight(),
            BTile.getWidth()));
    setPosition(BTile.getCoordinate());

    //TODO: change this temporary behavior when tile is bought
    //TODO: depend on if attribute is present
  }

  private VBox createBarBox(double width, double height, String color) {
    VBox barBox = new VBox();
    Rectangle topBar = createBackground(width, height / 6, Color.web(color), Color.BLACK);
    Rectangle bottomBar = createBackground(width, 5 * height / 6, Color.WHITE, Color.BLACK);
    barBox.getChildren().addAll(topBar, bottomBar);
    return barBox;
  }

  /**
   * @see Textable
   */
  @Override
  public VBox createTextBox(List info, double height, double width) {
    VBox textBox = new VBox();

    Text streetText = new Text(info.get(0).toString());
    resizeText(streetText, height, TEXT_SCALE, width);
    Bounds streetTextBounds = streetText.getBoundsInLocal();
    streetText.setLayoutY(this.getLayoutY());
    Text priceText = new Text(info.get(1).toString());
    resizeText(priceText, height, TEXT_SCALE, width);
    textBox.setMargin(priceText, new Insets((height / 4 - streetTextBounds.getMaxY()), 0, 0, 0));
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(streetText, priceText);
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

  private void setPosition(Coordinate coord) {
    this.setLayoutX(coord.getXCoor());
    this.setLayoutY(coord.getYCoor());
    this.getTransforms().add(new Rotate(coord.getAngle(), Rotate.Z_AXIS));
  }

}
