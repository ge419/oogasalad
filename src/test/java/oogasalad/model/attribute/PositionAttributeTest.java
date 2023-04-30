package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import oogasalad.view.Coordinate;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionAttributeTest {

  public static final String KEY = "Pos";
  public static final double ANGLE = 50.0;
  private PositionAttribute positionAttribute;
  private static final Double X_CORD = 0.0;

  private static final Double Y_CORD = 6.0;
  private static final Double NEW_X_CORD = 4.0;

  private static final Double NEW_Y_CORD = 5.0;


  @BeforeEach
  void setUp() {
    positionAttribute =
        new PositionAttribute("Pos",X_CORD, Y_CORD, ANGLE);
  }

  @Test
  void testValueProperty() {
    assertEquals(positionAttribute.xProperty().get(), positionAttribute.getX());
    assertEquals(positionAttribute.yProperty().get(), positionAttribute.getY());
    assertEquals(positionAttribute.angleProperty().get(), positionAttribute.getAngle());
  }

  @Test
  void testFrom() {
    Attribute attribute = PositionAttribute.from(positionAttribute);
    assertEquals(positionAttribute, attribute);
  }

  @Test
  void testGetCoordinate() {
    PositionAttribute testAttribute  = new PositionAttribute("Pos",X_CORD, Y_CORD, ANGLE);;
    assertEquals(testAttribute.toString(), positionAttribute.toString());
    testAttribute.setCoordinate(new Coordinate(5.0, 70.5, 30.4));
    assertEquals(testAttribute.getCoordinate().getXCoor(), 5.0);
    assertNotEquals(testAttribute.toString(), positionAttribute.toString());
  }

  @Test
  void testHashCode() {
    PositionAttribute sameAttribute  = new PositionAttribute(KEY,X_CORD, Y_CORD, ANGLE);
    PositionAttribute differentAttribute  = new PositionAttribute(KEY,X_CORD, 8.0, ANGLE);
    assertEquals(positionAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(positionAttribute.hashCode(), differentAttribute.hashCode());
  }

}