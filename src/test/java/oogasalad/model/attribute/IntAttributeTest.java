package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntAttributeTest {

  private IntAttribute attribute;

  @BeforeEach
  void setUp() {
    attribute = new IntAttribute("testKey", 10);
  }

  @Test
  void testVariableRetrieval() {
    assertNotNull(attribute);
    assertEquals(attribute.getKey(), "testKey");
    assertEquals(attribute.getValue(), 10);
  }

  @Test
  void testSetValue() {
    attribute.setValue(20);
    assertEquals(attribute.getValue(), 20);
  }

  @Test
  void testValueProperty() {
    assertNotNull(attribute.valueProperty());
    assertEquals(attribute.valueProperty().getValue(), 10);

    attribute.setValue(30);
    assertEquals(attribute.valueProperty().getValue(), 30);
  }

  @Test
  void testFrom() {

  }

  @Test
  void testEquals() {
    IntAttribute sameAttribute = new IntAttribute("testKey", 10);
    IntAttribute differentAttribute = new IntAttribute("testKey", 20);

    assertTrue(attribute.equals(sameAttribute));
    assertFalse(attribute.equals(differentAttribute));
  }

  @Test
  void testHashCode() {
    IntAttribute sameAttribute = new IntAttribute("testKey", 10);
    IntAttribute differentAttribute = new IntAttribute("testKey", 20);

    assertEquals(attribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(attribute.hashCode(), differentAttribute.hashCode());
  }

}