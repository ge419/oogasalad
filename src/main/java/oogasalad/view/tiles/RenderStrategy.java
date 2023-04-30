package oogasalad.view.tiles;

import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;

/**
 * <p> Strategy for creating and rendering different tile types
 *
 * <p>Assumptions: Assumes that once a new tile type is created, the user appends the appropriate
 * render strategy
 *
 * <p>Dependencies: Tile, ViewTile objects
 *
 * @author Woonggyu wj61
 */

public class RenderStrategy {

  /**
   * <p>This method takes in a backend tile and converts it into the appropriate frontend version.
   * Then it adds the frontend tile to the parent root (assumed to be a BorderPane) and adds it to
   * the list of all frontend tiles on the board.
   *
   * <p>Assumptions:  If one wants to make a new frontend tile type, they must make a new Tile type
   * by implementing the ViewTile interface as well as other helper interfaces. The only type of
   * tiles are street and image; all other types will be rendered as a basic tile:
   * <p>Parameters:
   *
   * @param BTile    the backend tile to be converted and added
   * @param pane     the BorderPane that the tile is to be added to
   * @param tileList list of all frontend tiles that are rendered.
   */
  protected void renderTile(Tile BTile, BorderPane pane, List<ViewTile> tileList) {
    switch (BTile.getViewType()) {
      case "street" -> {
        StreetTile tile = new StreetTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
      case "image" -> {
        ImageTile tile = new ImageTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
      default -> {
        BasicTile tile = new BasicTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
    }
  }
}
