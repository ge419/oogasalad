package oogasalad.view.builder.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import oogasalad.view.tiles.Tile;

public class ImmutableGraph implements GraphInterface{

  private final HashMap<Tile, List<Tile>> myStruct;

  public ImmutableGraph(GraphInterface graph){
    myStruct = new HashMap<>();
    for (Tile tile : graph.getTiles()){
      myStruct.put(tile, graph.getNextTiles(tile));
    }
  }

  @Override
  public List<Tile> getNextTiles(Tile desiredTile) {
    return myStruct.get(desiredTile);
  }

  @Override
  public List<Tile> getTiles() {
    return new ArrayList<>(myStruct.keySet());
  }

  @Override
  public String toString(){
    StringBuilder stringMaker = new StringBuilder();
    for (Tile tile : getTiles()){
      stringMaker.append("Tile ").append(tile.getTileId()).append(": ")
          .append(Arrays.asList(getNextTiles(tile)));
      // same as stringMaker.append("Tile " + tile.getTileId() + ": " + Arrays.asList(getNextTiles(tile)));
    }

    return stringMaker.toString();
  }

  @Override
  public boolean equals(Object o){
    if (o == null || o.getClass() != this.getClass()){
      return false;
    }
    return compareTilesAndNexts((ImmutableGraph) o);
  }

  private boolean compareTilesAndNexts(ImmutableGraph obj){
    for (Tile tile : getTiles()){
      if ( !obj.getTiles().contains(tile) ) return false;
      if (!new HashSet<>(obj.getNextTiles(tile)).containsAll(this.getNextTiles(tile))) return false;
    }
    return true;
  }

}
