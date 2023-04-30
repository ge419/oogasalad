package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColorAttributeTest {

  private static final String KEY = "testColor";
  private static final String VALUE = "red";
  ColorAttribute colorAttribute;
  Attribute attribute;

  @BeforeEach
  void setUp() {
    colorAttribute = new ColorAttribute(KEY, VALUE);
    attribute = mock(Attribute.class);
  }

  @Test
  public void testFrom() {
    Attribute attribute = ColorAttribute.from(colorAttribute);
    assertEquals(colorAttribute, attribute);
  }

}