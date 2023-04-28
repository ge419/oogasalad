package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionAttributeTest {

  public static final String KEY = "Pos";
  private PositionAttribute positionAttribute;
  private static final Double X_CORD = 0.0;

  private static final Double Y_CORD = 0.0;
  private static final Double NEW_X_CORD = 4.0;

  private static final Double NEW_Y_CORD = 5.0;

  private
  @BeforeEach
  void setUp() {
    positionAttribute =
        new PositionAttribute("Pos",X_CORD, Y_CORD, 50.0);
  }

  @AfterEach
  void testSetValue() {
  }

  @Test
  void testValueProperty() {
  }

  @Test
  void testFrom() {
  }

  @Test
  void testGetCoordinate() {

  }

  @Test
  void testEquals() {
    PositionAttribute sameAttribute  = new PositionAttribute(KEY,X_CORD, , 50.0);
    PositionAttribute differentAttribute  = new PositionAttribute(KEY,X_CORD, 8.0, 50.0);
    assertTrue(positionAttribute.equals(sameAttribute));
    assertFalse(positionAttribute.equals(differentAttribute));
  }

  @Test
  void testHashCode() {
    PositionAttribute sameAttribute  = new PositionAttribute(KEY,X_CORD, , 50.0);
    PositionAttribute differentAttribute  = new PositionAttribute(KEY,X_CORD, 8.0, 50.0);
    assertEquals(positionAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(positionAttribute.hashCode(), differentAttribute.hashCode());
  }
}