package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.beans.property.DoubleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoubleAttributeTest {

  private DoubleAttribute doubleAttribute;
  private static final String KEY = "doubleKey";
  private static final String NEW_KEY = "anotherKey";
  private static final Double VALUE = 1.0;
  private static final Double NEW_VALUE = 2.0;

  @BeforeEach
  public void setUp() {
    doubleAttribute = new DoubleAttribute(KEY, VALUE);
  }

  @Test
  public void testGetValue() {
    assertEquals(VALUE, doubleAttribute.getValue());
  }

  @Test
  public void testSetValue() {
    doubleAttribute.setValue(NEW_VALUE);
    assertEquals(NEW_VALUE, doubleAttribute.getValue());
  }

  @Test
  public void testEquals() {
    DoubleAttribute sameAttribute = new DoubleAttribute(KEY, VALUE);
    DoubleAttribute differentAttribute = new DoubleAttribute(NEW_KEY, VALUE);

    assertTrue(doubleAttribute.equals(sameAttribute));
    assertFalse(doubleAttribute.equals(differentAttribute));
  }

  @Test
  public void testHashCode() {
    DoubleAttribute sameAttribute = new DoubleAttribute(KEY, VALUE);
    DoubleAttribute differentAttribute = new DoubleAttribute(NEW_KEY, VALUE);

    assertNotEquals(doubleAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(doubleAttribute.hashCode(), differentAttribute.hashCode());
  }

  @Test
  public void testFrom() {
    Attribute attribute = DoubleAttribute.from(doubleAttribute);
    assertEquals(doubleAttribute, attribute);
  }
}