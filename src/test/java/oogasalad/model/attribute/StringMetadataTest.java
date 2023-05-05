package oogasalad.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringMetadataTest {

  private static final String KEY = "testStringMetadata";
  StringMetadata testStringMetaData;

  @BeforeEach
  public void setUp() {
    testStringMetaData = new StringMetadata(KEY);
  }

  @Test
  void testCheckPreconditions() {
    IntMetadata intMetadata = new IntMetadata("3");
    Attribute goodAttribute = testStringMetaData.makeAttribute();
    Attribute badAttribute = intMetadata.makeAttribute();
    assertTrue(testStringMetaData.checkPreconditions(goodAttribute));
    assertThrows(ClassCastException.class,
        () -> testStringMetaData.checkPreconditions(badAttribute));
  }

  @Test
  void getAttributeClass() {
    assertNotEquals(testStringMetaData.getAttributeClass(), ColorAttribute.class);
    assertEquals(testStringMetaData.getAttributeClass(), StringAttribute.class);
  }

  @Test
  void testGetDefaultValue() {
    assertNotEquals(testStringMetaData.getDefaultValue(), null);
  }

  @Test
  void testEquals() {
    StringMetadata same = new StringMetadata(KEY);
    assertEquals(testStringMetaData, same);
    same.setDefaultValue("54.0");
    assertNotEquals(testStringMetaData, same);
    assertNotEquals(null, testStringMetaData);

  }

  @Test
  void testHashCode() {
    StringMetadata sameContent = new StringMetadata(KEY);
    StringMetadata thing = testStringMetaData;
    assertNotEquals(sameContent.hashCode(), testStringMetaData.hashCode());
    assertEquals(testStringMetaData.hashCode(), thing.hashCode());
  }

  @Test
  void testToString() {
    StringMetadata same = new StringMetadata(KEY);
    assertEquals(same.toString(), testStringMetaData.toString());
  }
}