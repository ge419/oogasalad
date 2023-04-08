package oogasalad.view.builder;

import java.util.*;
import oogasalad.view.tiles.Tile;

public class Graph implements GraphInterface {
    private final HashMap<Tile, ArrayList<Tile>> myMap;

    Graph(){
        myMap = new HashMap<>();
    }
    @Override
    public void addTile(Tile tile) {
        initializeIfNonexistent(tile);
    }

    @Override
    public void addTileNext(Tile tile, Tile nextTile) {
        initializeIfNonexistent(tile);
        if (myMap.get(tile).contains(nextTile)){
            // todo: log that we tried to add a tile that already exists.
        }
        else{
            myMap.get(tile).add(nextTile);
        }
    }

    @Override
    public void removeNextTile(Tile tile, Tile nextTile) {
        initializeIfNonexistent(tile);
        if (myMap.get(tile).contains(nextTile)){
            myMap.get(tile).remove(nextTile);
        }
        else{
            // todo: log that we tried to remove a tile that isn't a next tile.
        }
    }

    @Override
    public int numberOfNextTiles(Tile desiredTile) {
        initializeIfNonexistent(desiredTile);
        return myMap.get(desiredTile).size();
    }

    @Override
    public List<Tile> getNextTiles(Tile desiredTile) {
        if (!myMap.containsKey(desiredTile)){
            // todo: create error for this.
            throw new RuntimeException();
        }
        return myMap.get(desiredTile);
    }

    @Override
    public List<Tile> getTiles() {
        return new ArrayList<>(myMap.keySet());
    }

    @Override
    public void print() {
        List<Tile> ourTiles = this.getTiles();
        int index = 0;
        for (Tile tile: ourTiles){
            System.out.println("Tile at index " + index + ": " + tile.toString());
            System.out.println(this.getNextTiles(tile));
            ++index;
        }
    }

    private void initializeIfNonexistent(Tile tile){
        myMap.putIfAbsent(tile, new ArrayList<>());
    }
}
