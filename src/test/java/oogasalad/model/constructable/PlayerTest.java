package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerTest {

  Player testPlayer;
  SchemaDatabase testSchemaDB;
  ObjectMapper testObjectMapper;
  Tile mockTile;

  @BeforeEach
  void setup() {
    mockTile = Mockito.mock(Tile.class);
    testObjectMapper = new ObjectMapper();
    testSchemaDB = new SimpleSchemaDatabase(testObjectMapper);
    testPlayer = new Player(testSchemaDB);
  }

  @Test
  void testDefaultValues() {
    assertNotNull(testPlayer.getScoreAttribute());
    assertNotNull(testPlayer.getName());
  }

  @Test
  void setTestPlayer() {
    assertNotNull(testPlayer.getColor());
    assertNotEquals(testPlayer.getScore(), 4.5);
    testPlayer.setScore(4.5);
    assertEquals(testPlayer.getScore(), 4.5);
  }

  @Test
  void testSetPlayerColor() {
    assertTrue(testPlayer.getCards().isEmpty());
    testPlayer.addCardToPlayer(mockTile);
    assertFalse(testPlayer.getCards().isEmpty());
  }
}