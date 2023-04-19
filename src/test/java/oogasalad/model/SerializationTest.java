package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleObjectSchema;
import oogasalad.model.attribute.StringMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SerializationTest {

  ObjectMapper mapper;
  Injector injector;
  SchemaDatabase database;

  private static ObjectSchema getSchema() {
    IntMetadata m1 = new IntMetadata("myInt");
    m1.setDefaultValue(5);
    StringMetadata m2 = new StringMetadata("myStr");
    m2.setDefaultValue("teststr");

    ObjectSchema schema = new SimpleObjectSchema("simple-schema", List.of(m1, m2));
    return schema;
  }

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    database = new SchemaDatabase();
    injector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(database)
    );
  }

  @Test
  void attributeSchemaSerialization() throws JsonProcessingException {
    ObjectSchema schema = getSchema();
    String json = mapper.writeValueAsString(schema);
    ObjectSchema actualSchema = mapper.readValue(json, SimpleObjectSchema.class);

    assertEquals(schema, actualSchema);
  }

  @Test
  void schemaDatabaseTest() throws JsonProcessingException {
    // SMOKE TEST: database should parse resource files
    assertTrue(database.getSchema("dieRule").isPresent());
    assertTrue(database.getSchema("basicTile").isPresent());
  }
}
