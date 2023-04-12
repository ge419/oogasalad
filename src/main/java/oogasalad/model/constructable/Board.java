package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class Board {
  private final List<Tile> tiles;

  public Board() {
    this.tiles = new ArrayList<>();
  }

  @JsonCreator
  public Board(@JsonProperty("tiles") List<Tile> t) {
    this.tiles = new ArrayList<>(t);
  }

  public List<Tile> getTiles() {
    return tiles;
  }
}
