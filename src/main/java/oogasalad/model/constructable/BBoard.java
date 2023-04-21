package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class BBoard {

  private final List<Tile> tiles;

  public BBoard() {
    this.tiles = new ArrayList<>();
  }

  @JsonCreator
  public BBoard(@JsonProperty("tiles") List<Tile> t) {
    this.tiles = new ArrayList<>(t);
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  @JsonIgnore
  public int getTileCount() {
    return this.tiles.size();
  }

  public void addTile(Tile t) {
    this.tiles.add(t);
  }

  public Tile getById(String id) {
    for (Tile t: tiles) {
      if (t.getId().equals(id)) return t;
    }
    return null;
  }

  public void remove(String id){
    tiles.removeIf(t -> t.getId().equals(id));
  }
}
