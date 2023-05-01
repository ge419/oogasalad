package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BBoardTest {

  BBoard testBoard;
  SchemaDatabase testSchemaDB;
  ObjectMapper testObjectMapper;
  Tile testTile;

  @BeforeEach
  void setup() {
    testObjectMapper = new ObjectMapper();
    testSchemaDB = new SimpleSchemaDatabase(testObjectMapper);
    testTile = new Tile(testSchemaDB);
    testBoard = new BBoard();
    testBoard.setTiles(List.of(testTile, testTile));
  }

  @Test
  void addTile() {
    Tile diffTile = new Tile(testSchemaDB);
    testBoard.addTile(diffTile);
    assertEquals(testBoard.getTiles().size(), 3);
  }

  @Test
  void getById() {
    assertTrue(testBoard.getById("null").isEmpty());
  }

  @Test
  void remove() {
    testBoard.remove("won");
  }
}