package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DoubleMetadataTest {
  private DoubleAttribute doubleAttribute;

  private DoubleMetadata metadata;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    metadata = new DoubleMetadata("test");
    doubleAttribute = mock(DoubleAttribute.class);
  }

  @Test
  public void testMakeAttribute() {
    Attribute attribute = metadata.makeAttribute();
    assertTrue(attribute instanceof DoubleAttribute);
    assertEquals("test", attribute.getKey());
    assertEquals(0.0, DoubleAttribute.from(attribute).getValue(), 0.01);
  }

  @Test
  public void testIsValidValue() {
    assertTrue(metadata.isValidValue(5.0));
    assertFalse(metadata.isValidValue(Double.NaN));
    assertFalse(metadata.isValidValue(Double.POSITIVE_INFINITY));
    assertFalse(metadata.isValidValue(Double.NEGATIVE_INFINITY));

    metadata.setMinValue(0.0);
    assertFalse(metadata.isValidValue(-1.0));

    metadata.setMaxValue(10.0);
    assertFalse(metadata.isValidValue(11.0));
  }

  @Test
  public void testCheckPreconditionsValid() {
    when(doubleAttribute.getValue()).thenReturn(2.0);
    assertTrue(metadata.checkPreconditions(doubleAttribute));
  }

  @Test
  public void testSetDefaultValue() {
    metadata.setDefaultValue(5.0);
    assertEquals(5.0, metadata.getDefaultValue(), 0.01);
  }

  @Test
  public void testSetMinValue() {
    metadata.setMinValue(-5.0);
    assertEquals(-5.0, metadata.getMinValue(), 0.01);
  }

  @Test
  public void testSetMaxValue() {
    metadata.setMaxValue(10.0);
    assertEquals(10.0, metadata.getMaxValue(), 0.01);
  }
}