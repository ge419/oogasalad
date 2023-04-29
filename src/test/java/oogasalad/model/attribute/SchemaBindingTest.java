package oogasalad.model.attribute;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SchemaBindingTest {

  private static final String SINK = "sinkText";
  private static final String SRC = "sourceText";

  @Test
  void testConstructorAndGetters() {
    SchemaBinding binding = new SchemaBinding(SINK, SRC);
    assertEquals(SINK, binding.sink());
    assertEquals(SRC, binding.source());
  }

  @Test
  void testToString() {
    SchemaBinding binding = new SchemaBinding(SINK, SRC);
    String expected = "SchemaBinding[sink=sinkText, source=sourceText]";
    assertEquals(expected, binding.toString());
  }

  @Test
  void testEqualsAndHashCode() {
    SchemaBinding binding1 = new SchemaBinding(SINK, SRC);
    SchemaBinding binding2 = new SchemaBinding(SINK, SRC);
    assertEquals(binding1, binding2);
    assertEquals(binding1.hashCode(), binding2.hashCode());
  }

}
