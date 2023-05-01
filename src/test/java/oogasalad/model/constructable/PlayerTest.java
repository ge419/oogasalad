package oogasalad.model.constructable;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleDoubleProperty;
import javax.print.SimpleDoc;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.PlayerAttribute;
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
  void setup(){
    mockTile  = Mockito.mock(Tile.class);
    testObjectMapper = new ObjectMapper();
    testSchemaDB =new SimpleSchemaDatabase(testObjectMapper);
    testPlayer = new Player(testSchemaDB);
  }

  @Test
  void testDefaultValues(){
    assertNotNull(testPlayer.getScoreAttribute());
    assertNotNull(testPlayer.getName());
  }

  @Test
  void setTestPlayer(){
    assertNotNull(testPlayer.getColor());
    assertNotEquals(testPlayer.getScore(), 4.5);
    testPlayer.setScore(4.5);
    assertEquals(testPlayer.getScore(), 4.5);
  }

  @Test
  void testSetPlayerColor(){
    assertTrue(testPlayer.getCards().isEmpty());
    testPlayer.addCardToPlayer(mockTile);
    assertFalse(testPlayer.getCards().isEmpty());
  }
}