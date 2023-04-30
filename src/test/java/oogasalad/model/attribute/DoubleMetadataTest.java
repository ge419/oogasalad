package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoubleMetadataTest {

  private DoubleAttribute doubleAttribute;
  private DoubleMetadata metadata;
  private static final String KEY = "doubleKey";

  @BeforeEach
  public void setUp() {
    metadata = new DoubleMetadata(KEY);
    doubleAttribute = mock(DoubleAttribute.class);
  }

  @Test
  public void testMakeAttribute() {
    Attribute attribute = metadata.makeAttribute();
    assertTrue(attribute instanceof DoubleAttribute);
    assertEquals(KEY, attribute.getKey());
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
  @Test
  void testEqual(){
    DoubleMetadata same = metadata;
    DoubleMetadata diff = new DoubleMetadata("diff");
    assertTrue(metadata.equals(same));
    assertEquals(metadata.hashCode(), same.hashCode());
    diff.setDefaultValue(3.9);
    assertFalse((metadata.equals(diff)));
  }
}