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
  void testCheckPreconditions() {

  }

  @Test
  void testMakeAttribute() {
    Attribute attribute = positionMetadata.makeAttribute();
    assertTrue(attribute instanceof PositionAttribute);
    assertEquals(KEY, attribute.getKey());
  }

  @Test
  void isValidCoordinate() {

  }

  @Test
  void from() {
  }

  @Test
  void makeCoordinateAttribute() {

  }

  @Test
  void getDefaultX() {
  }

  @Test
  void setDefaultX() {
  }

  @Test
  void defaultXProperty() {
  }

  @Test
  void getDefaultY() {
  }

  @Test
  void setDefaultY() {
  }

  @Test
  void defaultYProperty() {
  }

  @Test
  void getDefaultAngle() {
  }

  @Test
  void setDefaultAngle() {
  }

  @Test
  void defaultYAngle() {
  }

  @Test
  void testToString() {
  }

  @Test
  void testEquals() {
  }

  @Test
  void testHashCode() {
  }
}