package oogasalad.model.attribute;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AttributeInterfaceTest {

  @Test
  public void getKeyReturnsCorrectValue() {
    String key = "testKey";
    Attribute attribute = new TestAttribute(key);
    assertEquals(key, attribute.getKey());
  }

  private static class TestAttribute implements Attribute {

    private final String key;

    public TestAttribute(String key) {
      this.key = key;
    }

    @Override
    public String getKey() {
      return key;
    }
  }
}