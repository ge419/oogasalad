package oogasalad.model.attribute;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class SchemaUtilities {

  private SchemaUtilities() {
    throw new IllegalStateException("Utility class");
  }

  public static ObjectSchema concatenateSchemas(Collection<? extends ObjectSchema> schemaList) {
    return concatenateSchemas(UUID.randomUUID().toString(), schemaList);
  }

  public static ObjectSchema concatenateSchemas(String name,
      Collection<? extends ObjectSchema> schemaList) {
    List<Metadata> metadataList = schemaList.stream()
        .flatMap(schema -> schema.getAllMetadata().stream())
        .toList();

    return new SimpleObjectSchema(name, metadataList);
  }
}
