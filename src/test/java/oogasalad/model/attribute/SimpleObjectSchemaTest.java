package oogasalad.model.attribute;

import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.SimpleObjectSchema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class SimpleObjectSchemaTest {

  public static final String TEST_SCHEMA = "TestSchema";
  public static final String FIELD_1 = "field1";
  public static final String FIELD_2 = "field2";
  public static final String SCHEMA_2 = "AnotherSchema";

  @Test
  void testSimpleObjectSchemaCreation() {
    SimpleObjectSchema schema = new SimpleObjectSchema(TEST_SCHEMA);
    Assertions.assertEquals(TEST_SCHEMA, schema.getName());

    List<Metadata> fields = schema.getAllMetadata(

    );
    Assertions.assertTrue(fields.isEmpty());
  }

  @Test
  void testSimpleObjectSchemaWithMetadataCreation() {
    Metadata field1 = new StringMetadata(FIELD_1);
    Metadata field2 = new StringMetadata(FIELD_2);
    SimpleObjectSchema schema = new SimpleObjectSchema(TEST_SCHEMA, List.of(field1, field2));
    Assertions.assertEquals(TEST_SCHEMA, schema.getName());

    List<Metadata> fields = schema.getAllMetadata();
    Assertions.assertEquals(2, fields.size());
    Assertions.assertTrue(fields.contains(field1));
    Assertions.assertTrue(fields.contains(field2));

    Optional<Metadata> optionalField = schema.getMetadata(FIELD_1);
    Assertions.assertTrue(optionalField.isPresent());
    Assertions.assertEquals(field1, optionalField.get());
  }

  @Test
  void testSimpleObjectSchemaMakeAllAttributes() {
    Metadata field1 = new StringMetadata(FIELD_1);
    Metadata field2 = new StringMetadata(FIELD_2);
    SimpleObjectSchema schema = new SimpleObjectSchema(TEST_SCHEMA, List.of(field1, field2));

    List<Attribute> attributes = schema.makeAllAttributes();
    Assertions.assertEquals(2, attributes.size());

    Attribute attribute1 = attributes.get(0);
    Assertions.assertEquals(FIELD_1, attribute1.getKey());
    Attribute attribute2 = attributes.get(1);
    Assertions.assertEquals(FIELD_2, attribute2.getKey());
  }

  @Test
  void testSimpleObjectSchemaEqualsAndHashCode() {
    Metadata field1 = new StringMetadata(FIELD_1);
    Metadata field2 = new StringMetadata(FIELD_2);
    SimpleObjectSchema schema1 = new SimpleObjectSchema(TEST_SCHEMA, List.of(field1, field2));
    SimpleObjectSchema schema2 = new SimpleObjectSchema(TEST_SCHEMA, List.of(field1, field2));
    SimpleObjectSchema schema3 = new SimpleObjectSchema(SCHEMA_2, List.of(field1, field2));

    Assertions.assertEquals(schema1, schema2);
    Assertions.assertNotEquals(schema1, schema3);

    Assertions.assertEquals(schema1.hashCode(), schema2.hashCode());
    Assertions.assertNotEquals(schema1.hashCode(), schema3.hashCode());
  }

  @Test
  void testSimpleObjectSchemaToString() {
    SimpleObjectSchema schema = new SimpleObjectSchema(TEST_SCHEMA);
    Assertions.assertEquals("TestSchema Schema with MetaData", schema.toString());
  }
}
