package oogasalad.view.tiles;

import com.google.inject.Inject;
import java.util.Map;
import java.util.function.BiFunction;
import oogasalad.model.constructable.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p> Strategy for creating and rendering different tile types
 *
 * <p>Assumptions: Assumes that once a new tile type is created, the user appends the appropriate
 * render strategy
 *
 * <p>Dependencies: Tile, ViewTile objects
 *
 * @author dcm67, Woonggyu wj61
 */
public class TileRenderStrategy {

  private static final Logger LOGGER = LogManager.getLogger(TileRenderStrategy.class);
  private final ViewTileFactory factory;

  @Inject
  public TileRenderStrategy(ViewTileFactory factory) {
    this.factory = factory;
  }

  private final Map<String, BiFunction<ViewTileFactory, Tile, ViewTile>> renderMap =
      Map.of(
          "image", ViewTileFactory::createImageTile,
          "basic", ViewTileFactory::createBasicTile,
          "street", ViewTileFactory::createStreetTile,
          "custom", ViewTileFactory::createCustomTile
      );

  /**
   * <p> This method takes in a backend tile and converts it into the appropriate frontend version.
   *
   * <p>Assumptions:  If one wants to make a new frontend tile type, they must make a new Tile type
   * by implementing the ViewTile interface as well as other helper interfaces. The only type of
   * tiles are street, image, and custom; all other types will be rendered as a basic tile:
   * <p>Parameters:
   *
   * @param tile the backend tile to be converted and added
   */
  public ViewTile createViewTile(Tile tile) {
    String type = tile.getViewType();

    if (!renderMap.containsKey(type)) {
      LOGGER.error("View tile type does not exist");
      return factory.createBasicTile(tile);
    }

    return renderMap.get(type).apply(factory, tile);
  }
}
