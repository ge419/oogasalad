package oogasalad.view.builder;

import java.util.List;

public interface GraphInterface {
    void addTile(Tile tile);
    void addTileNext(Tile tile, Tile nextTile);
    void removeNextTile(Tile tile, Tile nextTile);
    int numberOfNextTiles(Tile desiredTile);
    List<Tile> getNextTiles(Tile desiredTile);
    List<Tile> getTiles();
}
