package oogasalad.model.attribute;

import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ImageMetadataTest {

  private StringProperty mockStringProperty;
  private ImageMetadata imageMetadata;
  private static final String KEY = "imageKey";

  @BeforeEach
  public void setUp() {
    mockStringProperty = mock(StringProperty.class);
    imageMetadata = new ImageMetadata(KEY);
  }

  @Test
  public void testMakeAttribute() {
    Attribute attribute = imageMetadata.makeAttribute();
    assertNotNull(attribute);
    assertTrue(attribute instanceof ImageAttribute);
    assertEquals(attribute.getKey(), KEY);
  }

  @Test
  public void testMakeImageAttribute() {
    ImageAttribute imageAttribute = imageMetadata.makeImageAttribute();
    assertNotNull(imageAttribute);
    assertEquals(imageAttribute.getKey(), KEY);
    assertEquals(imageAttribute.getValue(), "");
  }

  @Test
  public void testDefaultValue() {
    when(mockStringProperty.get()).thenReturn("");
    assertEquals(imageMetadata.getDefaultValue(), mockStringProperty.get());
  }
}
