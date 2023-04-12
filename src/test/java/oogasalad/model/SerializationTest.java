package oogasalad.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.List;
import oogasalad.model.attribute.AttributeSchema;
import oogasalad.model.attribute.CoordinateAttribute;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringMetadata;
import oogasalad.model.constructable.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationTest {
  ObjectMapper mapper;
  Injector injector;
  SchemaDatabase database;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    SchemaDatabase database = new SchemaDatabase();
    injector = Guice.createInjector(
        new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(database)
    );
  }

  @Test
  void attributeSchemaSerialization() throws JsonProcessingException {
    AttributeSchema schema = getSchema();
    String json = mapper.writeValueAsString(schema);
    AttributeSchema actualSchema = mapper.readValue(json, AttributeSchema.class);

    assertEquals(schema, actualSchema);
  }

  @Test
  void schemaDatabaseTest() throws JsonProcessingException {
    // SMOKE TEST: database should parse resource files
    assertTrue(database.getSchema("dieRule").isPresent());
    assertTrue(database.getSchema("basicTile").isPresent());
  }

  private static AttributeSchema getSchema() {
    IntMetadata m1 = new IntMetadata("myInt");
    m1.setDefaultValue(5);
    StringMetadata m2 = new StringMetadata("myStr");
    m2.setDefaultValue("teststr");

    AttributeSchema schema = new AttributeSchema("simple-schema", List.of(m1, m2));
    return schema;
  }
}
