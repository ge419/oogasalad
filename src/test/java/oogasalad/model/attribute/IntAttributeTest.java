package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntAttributeTest {

  private static final String KEY = "intKey";
  private IntAttribute intAttribute;

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

    assertEquals(intAttribute, sameAttribute);
    assertNotEquals(intAttribute, differentAttribute);
  }

  @Test
  void testHashCode() {
    IntAttribute sameAttribute = new IntAttribute(KEY, 10);
    IntAttribute differentAttribute = new IntAttribute(KEY, 20);

    assertEquals(intAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(intAttribute.hashCode(), differentAttribute.hashCode());
  }

}