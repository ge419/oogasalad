package oogasalad.view.builder.graphs;

import oogasalad.view.tiles.Tile;

public interface MutableGraph {
  void addTile(Tile tile);
  void addTileNext(Tile tile, Tile nextTile);
  void removeTile(Tile tile);
  void removeNextTile(Tile tile, Tile nextTile);
  int numberOfNextTiles(Tile desiredTile);
}
