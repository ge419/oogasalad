package oogasalad.view.tiles;

import com.google.inject.Inject;
import java.util.Map;
import java.util.function.BiFunction;
import oogasalad.model.constructable.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

  public ViewTile createViewTile(Tile tile) {
    String type = tile.getViewType();

    if (!renderMap.containsKey(type)) {
      LOGGER.error("View tile type does not exist");
      return factory.createBasicTile(tile);
    }

    return renderMap.get(type).apply(factory, tile);
  }
}
