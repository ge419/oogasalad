package oogasalad.view.builder;

public interface GraphInterface {
    void addTileNext(Tile tile, Tile nextTile);
    void removeNextTileFrom(Tile tile, Tile nextTile);
    void numberOfNextTiles(Tile desiredTile);
}
