package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceTest {

  SchemaDatabase testSchemaDB;
  ObjectMapper testObjectMapper;
  Piece testPiece;

  @BeforeEach
  void setup() {
    testObjectMapper = new ObjectMapper();
    testSchemaDB = new SimpleSchemaDatabase(testObjectMapper);
    testPiece = new Piece(testSchemaDB);
  }

  @Test
  void testGetImage() {
    assertEquals(testPiece.getImage(), "");
  }

  @Test
  void testTileProperty() {
    testPiece.setPlayer(new Player(testSchemaDB));
    assertEquals(testPiece.concreteTileProperty().get().getId(), testPiece.getTile().get().getId());
  }

  @Test
  void testRemove() {
    testPiece.clearTile();
    assertTrue(testPiece.getTile().isEmpty());
  }

}