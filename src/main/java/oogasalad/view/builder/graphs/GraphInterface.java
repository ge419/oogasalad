package oogasalad.view.builder.graphs;

import java.util.List;
import oogasalad.view.tiles.ViewTile;

@Deprecated
public interface GraphInterface {

  List<ViewTile> getNextTiles(ViewTile desiredTile);

  List<ViewTile> getTiles();

  void print();
}
