package oogasalad.model.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;

public class AbstractAttributeTest {

  private static final String KEY = "testKey";

  @Test
  public void testGetKey() {
    Attribute attr = mock(Attribute.class);
    when(attr.getKey()).thenReturn(KEY);
    assertEquals(KEY, attr.getKey());
  }

  @Test
  public void testGetAs() {
    Attribute attr = mock(Attribute.class);
    when(attr.getKey()).thenReturn(KEY);

    MockAttribute mockAttr = mock(MockAttribute.class);
    when(mockAttr.getKey()).thenReturn(KEY);

    assertEquals(mockAttr, AbstractAttribute.getAs(mockAttr, MockAttribute.class));
    assertEquals(mockAttr.getKey(), AbstractAttribute.getAs(mockAttr, Attribute.class).getKey());

    try {
      AbstractAttribute.getAs(attr, MockAttribute.class);
      fail("Expected ClassCastException to be thrown");
    } catch (ClassCastException e) {
      // expected
    }
  }

  private static class MockAttribute extends AbstractAttribute {

    private final StringProperty value;

    protected MockAttribute(String key, String value) {
      super(key);
      this.value = new SimpleStringProperty(value);
    }
  }
}