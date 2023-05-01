package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileListAttributeTest {

  private static final String KEY = "testTileAttribute";
  private static final List<String> TILE_IDS = List.of("0");
  private TileListAttribute tileListAttribute;

  @BeforeEach
  public void setUp() {
    tileListAttribute = new TileListAttribute(KEY, TILE_IDS);
  }

  @Test
  void testFrom() {
    Attribute attribute = TileListAttribute.from(tileListAttribute);
    assertEquals(tileListAttribute, attribute);
  }

  @Test
  void testGetTileIds() {
    assertEquals(tileListAttribute.getTileIds().get(0), "0");
  }

  @Test
  void testSetTileIds() {
    assertEquals(tileListAttribute.getTileIds().get(0), "0");
  }

  @Test
  void testTileIdsProperty() {
    assertEquals(tileListAttribute.tileIdsProperty().get(0), "0");
  }

  @Test
  void testToString() {
    TileListAttribute t = new TileListAttribute(KEY, TILE_IDS);
    assertEquals(t.toString(), tileListAttribute.toString());
  }
}