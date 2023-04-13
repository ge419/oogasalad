package oogasalad.view.builder.graphs;

import oogasalad.view.tiles.ViewTile;

public interface MutableGraph {

  void addTile(ViewTile tile);

  void addTileNext(ViewTile tile, ViewTile nextTile);

  void removeTile(ViewTile tile);

  void removeNextTile(ViewTile tile, ViewTile nextTile);

  int numberOfNextTiles(ViewTile desiredTile);
}
