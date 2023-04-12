package oogasalad.view.builder.graphs;

import oogasalad.view.tiles.Tile;

/**
 * MutableGraph will provide methods that allow you to directly modify a graph.
 *
 * @author tmh85
 */
public interface MutableGraph {

  /**
   * Adds a tile to a graph. Also initializes the next tiles for this current tile to be empty.
   *
   * @param tile tile you wish to add to the graph
   */
  void addTile(Tile tile);

  /**
   * Adds a next tile to a given tile.
   *
   * @param tile     current tile
   * @param nextTile next tile for the current tile
   */
  void addTileNext(Tile tile, Tile nextTile);

  /**
   * Removes a given tile and any next tiles it has from a graph.
   *
   * @param tile tile you wish to remove
   */
  void removeTile(Tile tile);

  /**
   * Remove a reference to a next tile for a given tile.
   *
   * @param tile     given tile
   * @param nextTile next tile that you wish to remove from given tile's references.
   */
  void removeNextTile(Tile tile, Tile nextTile);
}
