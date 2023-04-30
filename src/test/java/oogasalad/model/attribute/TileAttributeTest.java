package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileAttributeTest {

  private TileAttribute tileAttribute;
  private static final String  KEY = "testTileAttribute";
  private static final String ID = "0";

  @BeforeEach
  public void setUp() {
    tileAttribute = new TileAttribute(KEY, ID);
  }

  @Test
  void testFrom() {
    Attribute attribute = TileAttribute.from(tileAttribute);
    assertEquals(tileAttribute, attribute);
  }

}