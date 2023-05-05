package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntMetadataTest {

  private static final String KEY = "intMetaKey";
  private IntMetadata metadata;

  @BeforeEach
  void setUp() {
    metadata = new IntMetadata(KEY);
  }

  @Test
  void testIsValidValue() {
    assertTrue(metadata.isValidValue(0));
    assertTrue(metadata.isValidValue(Integer.MIN_VALUE));
    assertTrue(metadata.isValidValue(Integer.MAX_VALUE));
    metadata.setMaxValue(100);
    assertFalse(metadata.isValidValue(101));
    metadata.setMinValue(0);
    assertFalse(metadata.isValidValue(-5));
  }

  @Test
  void testGetDefaultValue() {
    assertEquals(0, metadata.getDefaultValue());
  }

  @Test
  void testSetDefaultValue() {
    metadata.setDefaultValue(42);
    assertEquals(42, metadata.getDefaultValue());
  }

  @Test
  void testDefaultValueProperty() {
    assertEquals(0, metadata.defaultValueProperty().getValue());
    metadata.defaultValueProperty().set(42);
    assertEquals(42, metadata.getDefaultValue());
  }

  @Test
  void testGetMinValue() {
    assertEquals(Integer.MIN_VALUE, metadata.getMinValue());
  }

  @Test
  void testSetMinValue() {
    metadata.setMinValue(-42);
    assertEquals(-42, metadata.getMinValue());
  }

  @Test
  void testMinValueProperty() {
    assertEquals(Integer.MIN_VALUE, metadata.minValueProperty().getValue());
    metadata.minValueProperty().set(-42);
    assertEquals(-42, metadata.getMinValue());
  }

  @Test
  void testGetMaxValue() {
    assertEquals(Integer.MAX_VALUE, metadata.getMaxValue());
  }

  @Test
  void testSetMaxValue() {
    metadata.setMaxValue(42);
    assertEquals(42, metadata.getMaxValue());
  }

  @Test
  void testMaxValueProperty() {
    assertEquals(Integer.MAX_VALUE, metadata.maxValueProperty().getValue());
    metadata.maxValueProperty().set(42);
    assertEquals(42, metadata.getMaxValue());
  }

  @Test
  void testCheckPreconditionsWithValidValue() {
    IntAttribute attribute = new IntAttribute("key", 100);
    metadata.setMaxValue(500);
    assertTrue(metadata.checkPreconditions(attribute));

    attribute.setValue(0);
    metadata.setMinValue(-100);
    assertTrue(metadata.checkPreconditions(attribute));
  }

  @Test
  void testCheckPreconditionsWithInvalidValue() {
    IntAttribute attribute = new IntAttribute("key", 100);
    metadata.setMaxValue(90);
    assertFalse(metadata.checkPreconditions(attribute));

    attribute.setValue(0);
    metadata.setMinValue(10);
    assertFalse(metadata.checkPreconditions(attribute));
  }

  @Test
  void testMakeAttribute() {
    Attribute attribute = metadata.makeAttribute();
    IntAttribute intFrom = IntAttribute.from(attribute);
    assertEquals(KEY, attribute.getKey());
    assertEquals(0, intFrom.getValue());
  }

  @Test
  void testGetAttributeClass() {
    assertEquals(IntAttribute.class, metadata.getAttributeClass());
  }

  @Test
  void testFrom() {
    IntMetadata meta = new IntMetadata("key");
    meta.setDefaultValue(5);
    meta.setMinValue(0);
    meta.setMaxValue(10);

    IntMetadata fromMeta = IntMetadata.from(meta);

    assertEquals(meta, fromMeta);
    assertEquals(meta.getDefaultValue(), fromMeta.getDefaultValue());
    assertEquals(meta.getMinValue(), fromMeta.getMinValue());
    assertEquals(meta.getMaxValue(), fromMeta.getMaxValue());
  }
}