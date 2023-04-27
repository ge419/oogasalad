package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BooleanMetadataTest {
  private BooleanMetadata metadata;
  private static final String KEY = "testBoolean";

  @BeforeEach
  public void setUp() {
    metadata = new BooleanMetadata(KEY);
  }

  @Test
  public void testMakeAttribute() {
    Attribute attribute = metadata.makeAttribute();
    assertTrue(attribute instanceof BooleanAttribute);
    assertEquals(KEY, attribute.getKey());
  }

  @Test
  public void testCheckPreconditions() {
    BooleanAttribute attribute = mock(BooleanAttribute.class);
    when(attribute.getKey()).thenReturn(KEY);
    when(attribute.getValue()).thenReturn(true);

    assertTrue(metadata.checkPreconditions(attribute));
  }

  @Test
  public void testGetDefaultValue() {
    assertFalse(metadata.getDefaultValue());
  }

  @Test
  public void testSetDefaultValue() {
    metadata.setDefaultValue(true);
    assertTrue(metadata.getDefaultValue());
  }
}