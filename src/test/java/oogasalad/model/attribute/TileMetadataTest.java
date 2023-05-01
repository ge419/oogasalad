package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileMetadataTest {

  private static final String KEY = "testTileMT";
  TileMetadata testTileMetadata;

  @BeforeEach
  void setup() {
    testTileMetadata = new TileMetadata(KEY);
  }

  @Test
  void testCheckPreconditions() {
    IntMetadata intMetadata = new IntMetadata("3");
    Attribute goodAttribute = testTileMetadata.makeAttribute();
    Attribute badAttribute = intMetadata.makeAttribute();
    assertTrue(testTileMetadata.checkPreconditions(goodAttribute));
    assertThrows(ClassCastException.class, () -> testTileMetadata.checkPreconditions(badAttribute));
  }


  @Test
  void testSettersAndGetters() {
    assertEquals(testTileMetadata.getDefaultValue(), "");
    testTileMetadata.setDefaultValue("tile2");
    assertNotEquals(testTileMetadata.getDefaultValue(), "");
  }


  @Test
  void testGetAttributeClass() {
    assertNotEquals(testTileMetadata.getAttributeClass(), ColorAttribute.class);
    assertEquals(testTileMetadata.getAttributeClass(), TileAttribute.class);
  }

  @Test
  void from() {
  }

  @Test
  void makeTileAttribute() {
    ColorMetadata colorMetadata = new ColorMetadata("bad");
    Attribute goodAttribute = testTileMetadata.makeAttribute();
    Attribute badAttribute = colorMetadata.makeAttribute();
    assertTrue(testTileMetadata.checkPreconditions(goodAttribute));
    assertThrows(ClassCastException.class, () -> testTileMetadata.checkPreconditions(badAttribute));
  }
}
