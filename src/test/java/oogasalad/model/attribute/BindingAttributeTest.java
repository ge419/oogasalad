package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BindingAttributeTest {

  BindingAttribute testBindingAttribute;
  private static final String KEY = "bindKey";

  private static final String VALUE = "bindValue";


  @BeforeEach
  void setup(){
    testBindingAttribute = new BindingAttribute(KEY,VALUE);
  }
  @Test
  void testFrom() {
    Attribute attribute = BindingAttribute.from(testBindingAttribute);
    assertEquals(testBindingAttribute, attribute);
  }
}