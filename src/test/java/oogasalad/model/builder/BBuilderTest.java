package oogasalad.model.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.view.Coordinate;
import oogasalad.view.tiles.BasicTile;
import oogasalad.view.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BBuilderTest {

  BBuilder builder;

  @BeforeEach
  void setup() {
    builder = new BBuilder();
  }

  @Test
  void testSaveTiles() throws IOException {
    Tile tile1 = new BasicTile(1, new Coordinate(0, 0));
    Tile tile2 = new BasicTile(2, new Coordinate(10, 10));
    Tile tile3 = new BasicTile(3, new Coordinate(20, 20));
    List<Tile> listTile = new ArrayList<>();
    listTile.add(tile1);
    listTile.add(tile2);
    listTile.add(tile3);
    String tileString = builder.saveTiles(listTile);

    // assertEquals();
  }

}
