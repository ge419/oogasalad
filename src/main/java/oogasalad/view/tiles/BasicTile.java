package oogasalad.view.tiles;

import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.model.attribute.PlayerAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.view.Coordinate;

public class BasicTile extends Rectangle implements ViewTile {

  private final Tile modelTile;

  @Inject
  public BasicTile(Tile tile) {
    super(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
//    xProperty().bind(tile.positionAttribute().xProperty());
//    yProperty().bind(tile.positionAttribute().yProperty());
//    widthProperty().bind(tile.widthAttribute().valueProperty());
//    heightProperty().bind(tile.heightAttribute().valueProperty());
    this.modelTile = tile;
    this.setFill(Color.LIGHTBLUE);
    this.setStroke(Color.BLACK);

    // Check if tiles have an owner attribute
    modelTile.getAttribute(BuyTileRule.OWNER_ATTRIBUTE)
        .map(PlayerAttribute::from)
        .map(PlayerAttribute::idProperty)
        .ifPresent(prop -> prop.addListener((observable, oldValue, newValue) ->
            newValue.ifPresentOrElse(
                // Tile is owned
                id -> this.setFill(Color.RED),
                // Tile is not owned
                () -> this.setFill(Color.LIGHTBLUE)
            )));
  }

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

  public String getTileId() {
    return this.modelTile.getId();
  }

  public Coordinate getPosition() {
    return modelTile.getCoordinate();
  }

  public void setPosition(Coordinate coord) {
    this.setX(coord.getXCoor());
    this.setY(coord.getYCoor());
  }

  public Paint getColor() {
    return this.getFill();
  }

  public void setColor(Color color) {
    this.setFill(color);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.getTileId() == ((BasicTile) o).getTileId();
  }
}
