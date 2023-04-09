package oogasalad.view.builder.graphs;

import java.util.List;
import oogasalad.view.tiles.Tile;

/**
 * Wrapper class that combines the methods in GraphInterface and MutableGraph.
 *
 * @author tmh85
 */
public abstract class AbstractGraph implements GraphInterface, MutableGraph {

  /**
   * @see GraphInterface#getNextTiles(Tile)
   */
  @Override
  abstract public List<Tile> getNextTiles(Tile desiredTile);

  /**
   * @see GraphInterface#getTiles()
   */
  @Override
  abstract public List<Tile> getTiles();

  /**
   * @see MutableGraph#addTile(Tile)
   */
  @Override
  abstract public void addTile(Tile tile);

  /**
   * @see MutableGraph#addTileNext(Tile, Tile)
   */
  @Override
  abstract public void addTileNext(Tile tile, Tile nextTile);

  /**
   * @see MutableGraph#removeTile(Tile)
   */
  @Override
  abstract public void removeTile(Tile tile);

  /**
   * @see MutableGraph#removeNextTile(Tile, Tile)
   */
  @Override
  abstract public void removeNextTile(Tile tile, Tile nextTile);

  /**
   * @see GraphInterface#numberOfNextTiles(Tile)
   */
  @Override
  abstract public int numberOfNextTiles(Tile desiredTile);
}
