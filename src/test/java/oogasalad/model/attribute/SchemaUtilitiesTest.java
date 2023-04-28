package oogasalad.model.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class SchemaUtilitiesTest {

  public static final String SCHEMA_1 = "schema1";
  public static final String SCHEMA_2 = "schema2";
  public static final String CONCATENATED = "concatenated";
  public static final String KEY_1 = "key1";
  public static final String KEY_2 = "key2";
  public static final String KEY_3 = "key3";
  public static final String KEY_4 = "key4";
  private Collection<ObjectSchema> schemaList;

  @Before
  public void setUp() {
    ObjectSchema schema1 = new SimpleObjectSchema(SCHEMA_1, List.of(
        new StringMetadata(KEY_1),
        new StringMetadata(KEY_2)
    ));
    ObjectSchema schema2 = new SimpleObjectSchema(SCHEMA_2, List.of(
        new StringMetadata(KEY_3),
        new StringMetadata(KEY_4)
    ));
    schemaList = Arrays.asList(schema1, schema2);
  }

  @Test
  public void concatenateSchemasContainsValidKeys() {
    ObjectSchema result = SchemaUtilities.concatenateSchemas(CONCATENATED, schemaList);

    assertNotNull(result);
    List<Metadata> metadataList = result.getAllMetadata();
    assertEquals(4, metadataList.size());
    assertEquals(KEY_1, metadataList.get(0).getKey());
    assertEquals(KEY_2, metadataList.get(1).getKey());
    assertEquals(KEY_3, metadataList.get(2).getKey());
    assertEquals(KEY_4, metadataList.get(3).getKey());
  }

  @Test
  public void concatenateSchemasWithoutName() {
    ObjectSchema result = SchemaUtilities.concatenateSchemas(schemaList);

    assertNotNull(result);
    List<Metadata> metadataList = result.getAllMetadata();
    assertEquals(4, metadataList.size());
    assertEquals(KEY_1, metadataList.get(0).getKey());
    assertEquals(KEY_2, metadataList.get(1).getKey());
    assertEquals(KEY_3, metadataList.get(2).getKey());
    assertEquals(KEY_4, metadataList.get(3).getKey());
  }


}
