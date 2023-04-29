package oogasalad.view.builder.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import oogasalad.view.tiles.ViewTile;

@Deprecated
public class Graph implements GraphInterface, MutableGraph {

  private final HashMap<ViewTile, ArrayList<ViewTile>> myMap;

  public Graph() {
    myMap = new HashMap<>();
  }

  @Override
  public void addTile(ViewTile tile) {
    initializeIfNonexistent(tile);
  }

  @Override
  public void addTileNext(ViewTile tile, ViewTile nextTile) {
    initializeIfNonexistent(tile);
    if (myMap.get(tile).contains(nextTile)) {
      // todo: log that we tried to add a tile that already exists.
    } else {
      myMap.get(tile).add(nextTile);
    }
  }

  @Override
  public void removeTile(ViewTile tile) {
    if (myMap.containsKey(tile)) {
      myMap.get(tile).clear();
      myMap.remove(tile);
      for (ViewTile otherTile : getTiles()) {
        myMap.get(otherTile).remove(tile);
      }
    } else {
      //todo: LOG that we tried to remove a tile that doesn't exist from the graph.
    }
  }

  @Override
  public void removeNextTile(ViewTile tile, ViewTile nextTile) {
    initializeIfNonexistent(tile);
    if (myMap.get(tile).contains(nextTile)) {
      myMap.get(tile).remove(nextTile);
    } else {
      // todo: log that we tried to remove a tile that isn't a next tile.
    }
  }

  @Override
  public int numberOfNextTiles(ViewTile desiredTile) {
    initializeIfNonexistent(desiredTile);
    return myMap.get(desiredTile).size();
  }

  @Override
  public List<ViewTile> getNextTiles(ViewTile desiredTile) {
    if (!myMap.containsKey(desiredTile)) {
      // todo: create error for this.
      throw new RuntimeException();
    }
    return myMap.get(desiredTile);
  }

  @Override
  public List<ViewTile> getTiles() {
    return new ArrayList<>(myMap.keySet());
  }

  @Override
  public void print() {
    List<ViewTile> ourTiles = this.getTiles();
    int index = 0;
    for (ViewTile tile : ourTiles) {
      System.out.println("Tile at index " + index + ": " + tile.toString());
      System.out.println(this.getNextTiles(tile));
      ++index;
    }
  }

  private void initializeIfNonexistent(ViewTile tile) {
    myMap.putIfAbsent(tile, new ArrayList<>());
  }
}
