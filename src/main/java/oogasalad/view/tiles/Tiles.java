package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;

/**
 * <p> Class that holds all Tiles to be rendered onto board
 *
 * <p>Assumptions: Assumes that the tile is shaped like a standard Monopoly street tile
 *
 * <p>Dependencies: Renderable interface, Tile object
 *
 * @author Woonggyu wj61
 */

public class Tiles implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> tileList = new ArrayList<>();
  private final TileRenderStrategy renderStrategy;

  @Inject
  public Tiles(@Assisted List<Tile> t, TileRenderStrategy renderStrategy) {
    this.BTiles = t;
    this.renderStrategy = renderStrategy;
  }

  /**
   * @see Renderable
   */
  @Override
  public void render(BorderPane pane) {
    for (Tile bTile : BTiles) {
      ViewTile viewTile = renderStrategy.createViewTile(bTile);
      pane.getChildren().add(viewTile.asNode());
      tileList.add(viewTile);
    }
  }

  /**
   * Get the frontend tile where the ID of its associated backend tile is the specified ID.
   *
   * <p>Assumptions: ID is passed in as a String
   *
   * <p>Parameters:
   * @param id is the ID of the backend tile that is associated with the frontend tile being found
   *
   * <p>Exceptions:
   * @throws IllegalArgumentException when there is no ViewTile with specified ID.

   *
   * @return Frontend tile associated with this ID
   */
  public ViewTile getTile(String id) {
    for (ViewTile tile : tileList) {
      if (tile.getTileId().equals(id)) {
        return tile;
      }
    }
    throw new IllegalArgumentException("No tile with id " + id);
  }
}
