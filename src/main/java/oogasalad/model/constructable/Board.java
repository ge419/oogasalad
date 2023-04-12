package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class Board {
  @JsonValue
  List<Tile> tiles = new ArrayList<>();

  public Board(List<Tile> t) {
    this.tiles = t;
  }
}
