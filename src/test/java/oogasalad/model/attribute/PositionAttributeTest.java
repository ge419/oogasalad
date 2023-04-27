package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionAttributeTest {

  private PositionAttribute positionAttribute;
  private static final Double X_CORD = 0.0;

  private static final Double Y_CORD = 0.0;
  private static final Double NEW_X_CORD = 4.0;

  private static final Double NEW_Y_CORD = 5.0;

  private
  @BeforeEach
  void setUp() {
    positionAttribute = new PositionAttribute("Pos",X_CORD, Y_CORD);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void setAngle() {
  }

  @Test
  void angleProperty() {
  }

  @Test
  void getCoordinate() {
  }

  @Test
  void setCoordinate() {
  }

  @Test
  void testToString() {
  }
}