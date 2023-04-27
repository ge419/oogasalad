package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.beans.property.DoubleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoubleAttributeTest {

  private DoubleAttribute doubleAttribute;

  @BeforeEach
  public void setUp() {
    doubleAttribute = new DoubleAttribute("testKey", 1.0);
  }

  @Test
  public void testGetValue() {
    assertEquals(1.0, doubleAttribute.getValue());
  }

  @Test
  public void testSetValue() {
    doubleAttribute.setValue(2.0);
    assertEquals(2.0, doubleAttribute.getValue());
  }

  @Test
  public void testEquals() {
    DoubleAttribute sameAttribute = new DoubleAttribute("testKey", 1.0);
    DoubleAttribute differentAttribute = new DoubleAttribute("differentKey", 1.0);

    assertTrue(doubleAttribute.equals(sameAttribute));
    assertFalse(doubleAttribute.equals(differentAttribute));
  }

  @Test
  public void testHashCode() {
    DoubleAttribute sameAttribute = new DoubleAttribute("testKey", 1.0);
    DoubleAttribute differentAttribute = new DoubleAttribute("differentKey", 1.0);

    assertNotEquals(doubleAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(doubleAttribute.hashCode(), differentAttribute.hashCode());
  }

  @Test
  public void testFrom() {
    Attribute attribute = DoubleAttribute.from(doubleAttribute);
    assertEquals(doubleAttribute, attribute);
  }
}