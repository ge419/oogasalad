package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionMetadataTest {

  private PositionMetadata positionMetadata;
  private static final String KEY = "testPosition";

  @BeforeEach
  public void setUp() {
    positionMetadata = new PositionMetadata(KEY);
  }

  @Test
  void testMakeAttribute() {
    Attribute attribute = positionMetadata.makeAttribute();
    assertTrue(attribute instanceof PositionAttribute);
    assertEquals(KEY, attribute.getKey());
  }

  @Test
  void testIsValidCoordinate() {
    ColorMetadata colorMetadata = new ColorMetadata("bad");
    Attribute goodAttribute = positionMetadata.makeAttribute();
    Attribute badAttribute  = colorMetadata.makeAttribute();
    assertTrue(positionMetadata.checkPreconditions(goodAttribute));
    assertThrows(ClassCastException.class, ()->positionMetadata.checkPreconditions(badAttribute) );
  }

  @Test
  void from() {
  }

  @Test
  void defaultYProperty() {
  }
  @Test
  void testToString() {
    PositionMetadata same = new PositionMetadata(KEY);
    assertEquals(same.toString(),positionMetadata.toString());
  }

  @Test
  void testSetters() {
    PositionMetadata pos = new PositionMetadata(KEY);
    pos.setDefaultX(1.0);
    pos.setDefaultY(54.0);
    pos.setDefaultAngle(43.0);
    assertEquals(pos.getDefaultY(), 54.0);
    assertEquals(pos.getDefaultX(), 1.0);
    assertNotEquals(pos.getDefaultAngle(), 43.1);
  }

  @Test
  void testHashCode() {
    PositionMetadata sameContent = new PositionMetadata(KEY);
    PositionMetadata thing = positionMetadata;
    assertNotEquals(sameContent.hashCode(),positionMetadata.hashCode());
    assertEquals(positionMetadata.hashCode(), thing.hashCode() );
  }
}