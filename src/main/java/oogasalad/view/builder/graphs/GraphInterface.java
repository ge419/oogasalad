package oogasalad.view.builder.graphs;

import java.util.List;
import oogasalad.view.tiles.Tile;

/**
 * Graphs will contain all the tiles in a game and any tiles they can go to next.
 *
 * @author tmh85
 */
public interface GraphInterface {

  /**
   * Returns all the next tiles for a given current tile.
   *
   * @param desiredTile current tile
   * @return list of tiles
   */
  List<Tile> getNextTiles(Tile desiredTile);

  /**
   * getTiles() will provide a list of tiles present on the game board.
   *
   * @return list of tiles
   */
  List<Tile> getTiles();

  /**
   * Returns the number of next tiles present for a given tile.
   *
   * @param desiredTile whatever tile you want to count its next tiles.
   * @return integer that is the number of next tiles that desiredTile has.
   */
  int numberOfNextTiles(Tile desiredTile);
}
