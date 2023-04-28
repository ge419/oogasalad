package oogasalad.view.tiles;

import com.google.inject.Inject;
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

public class StreetTile extends StackPane implements ViewTile, Textable, Backgroundable {

  private static final double TEXT_SCALE = 8;
  public static final String COLOR_ATTRIBUTE = "color";
  private final Tile modelTile;

  @Inject
  public StreetTile(Tile BTile) {
    this.modelTile = BTile;

    getChildren().addAll((createBarBox(BTile.getWidth(), BTile.getHeight(),
            StringAttribute.from(BTile.getAttribute(COLOR_ATTRIBUTE).get()).getValue())),
        createTextBox(BTile.getInfo(), BTile.getHeight(), BTile.getWidth()));
    setPosition(BTile.getCoordinate());

    //TODO: change this temporary behavior when tile is bought
    //TODO: depend on if attribute is present
    PlayerAttribute ownerAttribute =
        PlayerAttribute.from(modelTile.getAttribute(BuyTileRule.OWNER_ATTRIBUTE).get());
    ownerAttribute.idProperty().addListener(((observable, oldValue, isOwned) -> {
      if (isOwned.isPresent()) {
        for (Node child : this.getChildren()) {
          child.setStyle("-fx-background-color: red;");
        }
      } else {
        //do nothing
      }
    }));

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
    Rectangle topBar = createBackground(width, height / 6, Color.web(color), Color.BLACK);
    Rectangle bottomBar = createBackground(width, 5 * height / 6, Color.WHITE, Color.BLACK);
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
