package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractMetadataTest {

  private static final String ATTRIBUTE_KEY = "TestAttribute";
  private static final String METADATA_KEY = "TestMetadata";
  private Metadata metadata;

  @BeforeEach
  void setUp() {
    metadata = mock(AbstractMetadata.class);
  }

  @Test
  public void isValidReturnsTrueForMatchingClassAndValidAttributes() {
    ConcreteAttribute attribute = new ConcreteAttribute(ATTRIBUTE_KEY);
    ConcreteMetadata metadata = new ConcreteMetadata(METADATA_KEY);
    assertTrue(metadata.isValid(attribute));
  }

  @Test
  void isValidReturnsFalseForNonMatchingClass() {
    Attribute attribute = mock(Attribute.class);
    assertFalse(metadata.isValid(attribute));
  }

  @Test
  void isValidReturnsFalseForMatchingClassAndInvalidAttributes() {
    Attribute attribute = mock(Attribute.class);
    assertFalse(metadata.isValid(attribute));
  }

  @Test
  public void isCorrectTypeReturnsTrueForMatchingClass() {
    ConcreteAttribute attribute = new ConcreteAttribute(ATTRIBUTE_KEY);
    ConcreteMetadata metadata = new ConcreteMetadata(METADATA_KEY);
    assertTrue(metadata.isCorrectType(attribute));
  }

  @Test
  void isCorrectTypeReturnsFalseForNonMatchingClass() {
    Attribute attribute = mock(Attribute.class);
    assertFalse(metadata.isCorrectType(attribute));
  }

  @Test
  void getKeyReturnsCorrectValue() {
    String key = "test";
    Metadata metadata = new TestMetadata(key);
    assertEquals(key, metadata.getKey());
  }

  private static class TestMetadata extends AbstractMetadata {

    protected TestMetadata(String key) {
      super(key);
    }

    @Override
    protected boolean checkPreconditions(Attribute attribute) {
      return false;
    }

    @Override
    public Attribute makeAttribute() {
      return null;
    }

    @Override
    public Class<? extends Attribute> getAttributeClass() {
      return Attribute.class;
    }
  }
  private static class ConcreteAttribute extends AbstractAttribute {

    public ConcreteAttribute(String key) {
      super(key);
    }
  }

  private static class ConcreteMetadata extends AbstractMetadata {

    public ConcreteMetadata(String key) {
      super(key);
    }

    @Override
    protected boolean checkPreconditions(Attribute attribute) {
      return true;
    }

    @Override
    public Attribute makeAttribute() {
      return null;
    }

    @Override
    public Class<? extends Attribute> getAttributeClass() {
      return ConcreteAttribute.class;
    }
  }
}