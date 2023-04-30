package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringAttributeTest {

  private StringAttribute testStringAttribute;
  private static final String KEY = "testStringAttribute";
  private static final String VALUE = "testTileAttribute";


  @BeforeEach
  void setValue() {
    testStringAttribute = new StringAttribute(KEY, VALUE);
  }

  @Test
  void valueProperty() {
    assertEquals(testStringAttribute.getValue(), testStringAttribute.valueProperty().get());
  }

  @Test
  void testEquals() {
    StringAttribute same = new StringAttribute(KEY, VALUE);
    assertTrue(testStringAttribute.equals(same));
    same.setValue("something");
    assertFalse(testStringAttribute.equals(same));
  }

  @Test
  void testHashCode() {
    StringAttribute sameAttribute = new StringAttribute(KEY, VALUE);
    StringAttribute differentAttribute = new StringAttribute(KEY, "VALUE");
    assertEquals(testStringAttribute.hashCode(), sameAttribute.hashCode());
    assertNotEquals(testStringAttribute.hashCode(), differentAttribute.hashCode());
  }

  @Test
  void testToString() {
    StringAttribute sameAttribute = new StringAttribute(KEY, VALUE);
    StringAttribute differentAttribute = new StringAttribute(KEY, "VALUE");
    assertEquals(testStringAttribute.toString(), sameAttribute.toString());
    assertNotEquals(testStringAttribute.toString(), differentAttribute.toString());
  }
}