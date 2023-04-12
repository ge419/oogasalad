package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.SchemaDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Superclass for all objects that contain attributes.
 * The available attributes are defined by the schema.
 */
@JsonTypeInfo(use= Id.CLASS)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class GameConstruct {
  private static final Logger LOGGER = LogManager.getLogger(GameConstruct.class);
  @JsonProperty("schema")
  private String schemaName;
  @JsonIgnore
  private final SchemaDatabase database;
  private final Map<String, Attribute> attributeMap;

  public GameConstruct(String schemaName) {
    this.schemaName = schemaName;
    database = null;
    attributeMap = new TreeMap<>();
  }

  public GameConstruct(String schemaName, SchemaDatabase database) {
    // TODO ??
    this.schemaName = schemaName;
    this.database = database;
    this.attributeMap = new TreeMap<>();
    this.loadSchema(schemaName);
  }

  @JsonGetter("attributes")
  public List<Attribute> getAllAttributes() {
    return attributeMap.values().stream().toList();
  }

  @JsonSetter("attributes")
  public void setAllAttributes(List<Attribute> attributeList) {
    attributeMap.clear();
    for (Attribute attribute : attributeList) {
      attributeMap.put(attribute.getKey(), attribute);
    }
  }

  @JsonIgnore
  public Attribute getAttribute(String key) {
    return attributeMap.get(key);
  }

  @JsonIgnore
  public void setAttribute(String key, Attribute value) {
    ObjectSchema schema = getSchema();
    Optional<Metadata> optionalMetadata = schema.getMetadata(key);

    if (optionalMetadata.isEmpty()) {
      LOGGER.error("tried to set key {} that is not in schema {}", key, schema.getName());
      throw new IllegalArgumentException("invalid key for schema");
    }

    Metadata metadata = optionalMetadata.get();
    if (!metadata.isCorrectType(value)) {
      LOGGER.error("tried to set {} to {} when schema requires {}",
          key, value.getClass(), metadata.getAttributeClass());
      throw new IllegalArgumentException("conflicting keys with different types");
    }

    if (!metadata.getKey().equals(value.getKey())) {
      LOGGER.error("metadata key {} and value key {} conflict",
          metadata.getKey(), value.getKey());
      throw new IllegalArgumentException("conflicting metadata/value keys");
    }

    attributeMap.put(key, value);
  }

  @JsonGetter("schema")
  public String getSchemaName() {
    return schemaName;
  }

  @JsonSetter("schema")
  public void loadSchema(String newSchemaName) {
    if (!database.containsSchema(newSchemaName)) {
      LOGGER.error("schema does not exist {}", newSchemaName);
      throw new IllegalArgumentException("invalid schema");
    }
    this.schemaName = newSchemaName;
    ObjectSchema newSchema = database.getSchema(schemaName).get();
    setAllAttributes(newSchema.makeAllAttributes());
    migrateAttributes(attributeMap);
  }

  /**
   * Migrate attributes from a map if they exist in the current schema.
   */
  private void migrateAttributes(Map<String, Attribute> oldAttributes) {
    for (Metadata metadata : getSchema().getAllMetadata()) {
      String key = metadata.getKey();
      if (oldAttributes.containsKey(key)) {
        setAttribute(key, oldAttributes.get(key));
      }
    }
  }

  public ObjectSchema getSchema() {
    // TODO: Add rule schemas
    Optional<ObjectSchema> schema = database.getSchema(getSchemaName());
    if (schema.isEmpty()) {
      LOGGER.error("contained schema name {} is not in database", getSchemaName());
      throw new IllegalStateException("invalid contained schema");
    }
    return schema.get();
  }
}