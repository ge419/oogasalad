package oogasalad.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import oogasalad.model.attribute.AttributeList;
import oogasalad.model.attribute.AttributeSchema;
import oogasalad.model.attribute.CoordinateAttribute;
import oogasalad.model.attribute.CoordinateAttributeMetadata;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.IntAttributeMetadata;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.StringAttributeMetadata;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationTest {
  ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
  }

  @Test
  void attributeListSerialization() throws JsonProcessingException {
    AttributeSchema schema = getSchema();
    AttributeList attributeList = schema.makeAllAttributes();

    String json = mapper.writeValueAsString(attributeList);
    AttributeList actualList = mapper.readValue(json, AttributeList.class);

    assertEquals(
        5
        , IntAttribute.from(attributeList.getAttribute("myInt").get()).getValue()
    );
    assertEquals(
        "teststr"
        , StringAttribute.from(attributeList.getAttribute("myStr").get()).getValue()
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
    SchemaDatabase database = new SchemaDatabase();
    assertTrue(database.getSchema("dieRule").isPresent());
    assertTrue(database.getSchema("basicTile").isPresent());
  }

  private static AttributeSchema getSchema() {
    IntAttributeMetadata m1 = new IntAttributeMetadata("myInt");
    m1.setDefaultValue(5);
    StringAttributeMetadata m2 = new StringAttributeMetadata("myStr");
    m2.setDefaultValue("teststr");

    AttributeSchema schema = new AttributeSchema("simple-schema", List.of(m1, m2));
    return schema;
  }
}
