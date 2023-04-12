package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import oogasalad.model.exception.ResourceReadException;
import oogasalad.model.exception.FileReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This utility class hold all metaData and is useful for determining the parameter type that a BMetaData takes or an
 * */
public class SchemaDatabase {

  private static final Logger logger = LogManager.getLogger(SchemaDatabase.class);
  public static final String SCHEMA_RESOURCE_PATH = "schemas";
  private final Map<String, ObjectSchema> schemaMap;


  public SchemaDatabase() {
    schemaMap = new HashMap<>();
    this.readResourceSchemaFiles();
  }

  private void readResourceSchemaFiles() {
    try {
      for (File file : FileReader.readFiles(SCHEMA_RESOURCE_PATH)) {
        readSchemaFile(file);
      }
    } catch (IOException | FileReaderException e) {
      logger.fatal("Failed to read resource schema file", e);
      throw new ResourceReadException(e);
    }
  }

  private void readSchemaFile(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectSchema schema = mapper.readValue(file, ObjectSchema.class);
    String schemaName = schema.getName();

    if (schemaMap.containsKey(schemaName)) {
      logger.error("Duplicate schema name {}", schemaName);
    }
    schemaMap.put(schemaName, schema);
  }

  public Optional<ObjectSchema> getSchema(String name) {
    return Optional.ofNullable(schemaMap.get(name));
  }

  public boolean containsSchema(String name) {
    return schemaMap.containsKey(name);
  }
}
