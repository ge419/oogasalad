package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BBoard {

  private List<Tile> tiles;

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
  public void setTiles(List<Tile> tiles) {this.tiles = tiles;}

  @JsonIgnore
  public int getTileCount() {
    return this.tiles.size();
  }

  @JsonIgnore
  public void addTile(Tile t) {
    this.tiles.add(t);
  }

  @JsonIgnore
  public Optional<Tile> getById(String id) {
    for (Tile t : tiles) {
      if (t.getId().equals(id)) {
        return Optional.of(t);
      }
    }

    return Optional.empty();
  }

  @JsonIgnore
  public void remove(String id){
    tiles.removeIf(t -> t.getId().equals(id));
  }
}
