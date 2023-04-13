package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
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
}
