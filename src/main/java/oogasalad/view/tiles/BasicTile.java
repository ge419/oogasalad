package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import oogasalad.model.attribute.PlayerAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.view.Coordinate;

/**
 * <p>A basic implementation of a ViewTile.</p>
 * <p>This type of tile is a basic rectangle that supports different kinds of colors.</p>
 *
 * @author tmh85, Woonggyu wj61
 */
public class BasicTile extends Rectangle implements ViewTile {

  private final Tile myModelTile;

  @Inject
  public BasicTile(@Assisted Tile tile) {
    super(0, 0, tile.getWidth(), tile.getHeight());
    this.myModelTile = tile;

    this.setFill(Paint.valueOf(myModelTile.colorAttribute().getValue()));

    myModelTile.colorAttribute().valueProperty().addListener(((observable, oldValue, newValue) -> {
      this.setFill(Paint.valueOf(newValue));
    }));
    this.setStroke(Color.BLACK);

    // Check if tiles have an owner attribute
    myModelTile.getAttribute(BuyTileRule.OWNER_ATTRIBUTE)
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

  /**
   * @return backend tile associated with this frontend tile
   */
  public Tile getTile() {
    return this.myModelTile;
  }

  /**
   * Set the size of this frontend tile
   */
  @Override
  public void setSize(double width, double height) {
    this.setWidth(width);
    this.setHeight(height);
  }

  @Override
  public Node asNode() {
    return this;
  }

  /**
   * Return the ID of the backend tile associated with this frontend tile
   */
  public String getTileId() {
    return this.myModelTile.getId();
  }

  /**
   * Return Coordinate object associated with this frontend tile
   */
  public Coordinate getPosition() {
    return myModelTile.getCoordinate();
  }

  /**
   * Set position of this frontend tile
   */
  private void setPosition(Coordinate coord) {
    this.setX(coord.getXCoor());
    this.setY(coord.getYCoor());
  }

  /**
   * Get the color of this frontend tile
   */
  public Paint getColor() {
    return this.getFill();
  }

  /**
   * Set the color of this frontend tile
   */
  public void setColor(Color color) {
    this.setFill(color);
  }

  /**
   * Check for tile equality
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.getTileId().equals(((BasicTile) o).getTileId());
  }
}
