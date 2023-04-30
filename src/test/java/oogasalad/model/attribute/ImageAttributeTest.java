package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageAttributeTest {

  ImageAttribute imageAttribute;

  @BeforeEach
  public void setUp() {
    imageAttribute = new ImageAttribute("imageKey", "imageSrc");
  }

  @Test
  public void testFrom() {
    Attribute attribute = ImageAttribute.from(imageAttribute);
    assertEquals(imageAttribute, attribute);
  }

}