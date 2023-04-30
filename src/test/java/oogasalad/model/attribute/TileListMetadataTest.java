package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TileListMetadataTest {

  TileListMetadata tileListMetadata;
  private static final String KEY = "testTileListMetadata";

  @BeforeEach
  void setup(){
    tileListMetadata = new TileListMetadata(KEY);
  }
  @Test
  void testCheckPreconditions() {
    ColorMetadata colorMetadata = new ColorMetadata("bad");
    Attribute goodAttribute = tileListMetadata.makeAttribute();
    Attribute badAttribute  = colorMetadata.makeAttribute();
    assertTrue(tileListMetadata.checkPreconditions(goodAttribute));
    assertThrows(ClassCastException.class, ()->tileListMetadata.checkPreconditions(badAttribute) );
  }


  @Test
  void testGetAttributeClass() {
    assertNotEquals(tileListMetadata.getAttributeClass(), ColorAttribute.class);
    assertEquals(tileListMetadata.getAttributeClass(), TileListAttribute.class);
  }

  @Test
  void testFrom() {
    Metadata tileList = new TileListMetadata(KEY);
    assertEquals(tileListMetadata.from(tileList).getAttributeClass(), TileListAttribute.class);
  }

  @Test
  void testMakeTileListAttribute() {
    assertNotNull(tileListMetadata.makeTileListAttribute().getKey());
  }
}