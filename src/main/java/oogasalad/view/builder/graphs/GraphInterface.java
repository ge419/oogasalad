package oogasalad.view.builder.graphs;

import java.util.List;
import oogasalad.view.tiles.Tile;

public interface GraphInterface {
    List<Tile> getNextTiles(Tile desiredTile);
    List<Tile> getTiles();
    void print();
}
