package oogasalad.view.builder.graphs;

import java.util.List;
import oogasalad.view.tiles.Tile;

public abstract class AbstractGraph implements GraphInterface, MutableGraph {

  @Override
  abstract public List<Tile> getNextTiles(Tile desiredTile);

  @Override
  abstract public List<Tile> getTiles();

  @Override
  abstract public void addTile(Tile tile);

  @Override
  abstract public void addTileNext(Tile tile, Tile nextTile);

  @Override
  abstract public void removeTile(Tile tile);

  @Override
  abstract public void removeNextTile(Tile tile, Tile nextTile);

  @Override
  abstract public int numberOfNextTiles(Tile desiredTile);
}
