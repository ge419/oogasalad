package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntAttributeTest {

  private IntAttribute intAttribute;
  private static final String KEY = "intKey";

  @BeforeEach
  void setUp() {
    intAttribute = new IntAttribute(KEY, 10);
  }

  @Test
  void testVariableRetrieval() {
    assertNotNull(intAttribute);
    assertEquals(intAttribute.getKey(), KEY);
    assertEquals(intAttribute.getValue(), 10);
  }

  @Test
  void testSetValue() {
    intAttribute.setValue(20);
    assertEquals(intAttribute.getValue(), 20);
  }

  @Test
  void testValueProperty() {
    assertNotNull(intAttribute.valueProperty());
    assertEquals(intAttribute.valueProperty().getValue(), 10);

    intAttribute.setValue(30);
    assertEquals(intAttribute.valueProperty().getValue(), 30);
  }

  @Test
  void testFrom() {
    Attribute attribute = IntAttribute.from(intAttribute);
    assertEquals(intAttribute, attribute);
  }

  @Test
  void testEquals() {
    IntAttribute sameAttribute = new IntAttribute(KEY, 10);
    IntAttribute differentAttribute = new IntAttribute(KEY, 20);

    assertTrue(intAttribute.equals(sameAttribute));
    assertFalse(intAttribute.equals(differentAttribute));
  }

  @Test
  void testHashCode() {
    IntAttribute sameAttribute = new IntAttribute(KEY, 10);
    IntAttribute differentAttribute = new IntAttribute(KEY, 20);

    assertEquals(intAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(intAttribute.hashCode(), differentAttribute.hashCode());
  }

}