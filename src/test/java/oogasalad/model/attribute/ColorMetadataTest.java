package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColorMetadataTest {

  private static final String KEY = "color";
  private static final String DEFAULT_TEST = "000000";
  private ColorMetadata colorMetadata;
  private StringProperty defaultValueMock;

  @BeforeEach
  void setUp() {
    colorMetadata = new ColorMetadata(KEY);
    defaultValueMock = mock(StringProperty.class);
    colorMetadata.setDefaultValue(defaultValueMock.get());
  }

  @Test
  void testMakeAttribute() {
    Attribute attribute = colorMetadata.makeAttribute();
    assertEquals(attribute.getKey(), KEY);
    assertEquals(ColorAttribute.class, attribute.getClass());
  }

  @Test
  void testGetDefaultValue() {
    assertEquals(defaultValueMock.get(), colorMetadata.getDefaultValue());
  }

  @Test
  void testSetDefaultValue() {
    colorMetadata.setDefaultValue(DEFAULT_TEST);
    assertEquals(DEFAULT_TEST, colorMetadata.getDefaultValue());
  }
}