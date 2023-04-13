package oogasalad.view.builder.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import oogasalad.view.tiles.ViewTile;

/**
 * Implementation of just the GraphInterface. Note that this Graph is immutable, meaning that no
 * tiles or next tiles can be added to it. Thus, in order to use this, you must pass a graph that
 * already contains values in it.
 *
 * @author tmh85
 */
public class ImmutableGraph implements GraphInterface {

  private final HashMap<ViewTile, List<ViewTile>> myStruct;

  /**
   * Constructs an Immutable version of a given graph
   *
   * @param graph Some graph that already contains data.
   */
  public ImmutableGraph(GraphInterface graph) {
    myStruct = new HashMap<>();
    for (ViewTile tile : graph.getTiles()) {
      myStruct.put(tile, graph.getNextTiles(tile));
    }
  }

  /**
//   * @see GraphInterface#getNextTiles(Tile)
   */
  @Override
  public List<ViewTile> getNextTiles(ViewTile desiredTile) {
    return myStruct.get(desiredTile);
  }

  /**
   * @see GraphInterface#getTiles()
   */
  @Override
  public List<ViewTile> getTiles() {
    return new ArrayList<>(myStruct.keySet());
  }

  @Override
  public void print() {

  }

  /**
   * @see Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder stringMaker = new StringBuilder();
    for (ViewTile tile : getTiles()) {
      stringMaker.append("Tile ").append(tile.getTileId()).append(": ")
          .append(Arrays.asList(getNextTiles(tile)));
      // same as stringMaker.append("Tile " + tile.getTileId() + ": " + Arrays.asList(getNextTiles(tile)));
    }

    return stringMaker.toString();
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }
    return compareTilesAndNexts((ImmutableGraph) o);
  }

  /**
   * Compares each tile and it's next tiles. Note that <em>order matters</em> for this!
   *
   * @param obj graph we are comparing ourself to
   * @return false if there are any differences, true if not.
   */
  private boolean compareTilesAndNexts(GraphInterface obj) {
    for (ViewTile tile : getTiles()) {
      if (!obj.getTiles().contains(tile)) {
        return false;
      }
      if (!new HashSet<>(obj.getNextTiles(tile)).containsAll(this.getNextTiles(tile))) {
        return false;
      }
    }
    return true;
  }

}







