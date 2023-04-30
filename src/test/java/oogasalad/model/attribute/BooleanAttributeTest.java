package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class BooleanAttributeTest {

  @Mock
  Attribute attribute;
  private static final String KEY = "testBoolean";
  private static final boolean VALUE = true;
  private BooleanAttribute booleanAttribute;

  @BeforeEach
  void setUp() {
    booleanAttribute = new BooleanAttribute(KEY, true);
    attribute = mock(Attribute.class);
  }

  @Test
  public void testFrom() {
    Attribute attribute = BooleanAttribute.from(booleanAttribute);
    assertEquals(booleanAttribute, attribute);
  }

  @Test
  void testGetValue() {
    when(attribute.getKey()).thenReturn("testKey");
    assertEquals(true, booleanAttribute.getValue());
    assertNotNull(booleanAttribute.valueProperty());
  }

  @Test
  public void testSetValue() {
    booleanAttribute.setValue(!VALUE);
    assertEquals(!VALUE, booleanAttribute.getValue());
  }

  @Test
  void testEqual(){
    BooleanAttribute same = booleanAttribute;
    BooleanAttribute diff = new BooleanAttribute("diff", true);
    assertTrue(booleanAttribute.equals(same));
    assertEquals(booleanAttribute.hashCode(), same.hashCode());
    diff.setValue(false);
    assertFalse((booleanAttribute.equals(diff)));
  }
}

