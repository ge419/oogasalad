package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
  }

  @Test
  public void testSetValue() {
    booleanAttribute.setValue(!VALUE);
    assertEquals(!VALUE, booleanAttribute.getValue());
  }

}