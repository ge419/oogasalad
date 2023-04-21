package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SchemaUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Superclass for all objects that contain attributes. The available attributes are defined by the
 * schema.
 */
@JsonTypeInfo(use = Id.CLASS)
public abstract class AbstractGameConstruct implements GameConstruct {

  private static final Logger LOGGER = LogManager.getLogger(AbstractGameConstruct.class);
  @JsonIgnore
  private final SchemaDatabase database;
  private final Map<String, Attribute> attributeMap;
  @JsonProperty("schemas")
  private List<String> schemaNames;
  private String id;
  private final ObjectProperty<ObjectSchema> schemaProperty;

  protected AbstractGameConstruct(List<String> schemaNames, SchemaDatabase database) {
    this.id = UUID.randomUUID().toString();
    this.database = database;
    this.attributeMap = new TreeMap<>();
    this.schemaProperty = new SimpleObjectProperty<>();

    database.databaseProperty().addListener((observable, oldValue, newValue) -> refreshSchema());
    setSchemaNames(schemaNames);
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @JsonGetter("attributes")
  public List<Attribute> getAllAttributes() {
    return attributeMap.values().stream().toList();
  }

  @JsonSetter("attributes")
  public void setAllAttributes(List<Attribute> attributeList) {
    for (Attribute attribute : attributeList) {
      attributeMap.put(attribute.getKey(), attribute);
    }

    reconcileAttributes();
  }

  @Override
  @JsonIgnore
  public Attribute getAttribute(String key) {
    return attributeMap.get(key);
  }

  @JsonGetter("schemas")
  public List<String> getSchemaNames() {
    return schemaNames;
  }

  private void refreshSchema() {
    setSchemaNames(schemaNames);
  }

  @JsonIgnore
  protected void setSchemaNames(List<String> newSchemaNames) {
    List<ObjectSchema> schemas = new ArrayList<>();
    for (String newSchemaName : newSchemaNames) {
      Optional<ObjectSchema> schemaOptional = database.getSchema(newSchemaName);
      if (schemaOptional.isEmpty()) {
        LOGGER.warn("schema does not exist {}", newSchemaNames);
      } else {
        schemas.add(schemaOptional.get());
      }
    }

    this.schemaNames = new ArrayList<>(newSchemaNames);
    ObjectSchema newSchema = SchemaUtilities.concatenateSchemas(schemas);
    this.schemaProperty.setValue(newSchema);
    reconcileAttributes();
  }

  /**
   * Reconcile current attributes with the current schema, migrating attributes when possible. This
   * purposely keeps old attributes in the map so that deleted attributes retain their value if
   * added back.
   * TODO: add a mechanism to delete unused attributes before serialization?
   */
  private void reconcileAttributes() {
    for (Metadata metadata : getSchema().getAllMetadata()) {
      String key = metadata.getKey();
      boolean canKeepAttribute = attributeMap.containsKey(key)
          && canMigrateAttribute(attributeMap.get(key), metadata);
      if (!canKeepAttribute) {
        attributeMap.put(key, metadata.makeAttribute());
      }
    }
  }

  private boolean canMigrateAttribute(Attribute attribute, Metadata metadata) {
    if (!metadata.isCorrectType(attribute)) {
      LOGGER.info("tried to set {} to {} when schema requires {}",
          metadata.getKey(), attribute.getClass(), metadata.getAttributeClass());
      return false;
    }

    if (!metadata.getKey().equals(attribute.getKey())) {
      LOGGER.info("metadata key {} and value key {} conflict",
          metadata.getKey(), attribute.getKey());
      return false;
    }

    if (!metadata.isValid(attribute)) {
      LOGGER.info("attribute {} invalid for metadata {}",
          attribute, metadata);
      return false;
    }

    return true;
  }

  @Override
  public ObjectSchema getSchema() {
    return schemaProperty.get();
  }

  @Override
  @JsonIgnore
  public ReadOnlyObjectProperty<ObjectSchema> schemaProperty() {
    return schemaProperty;
  }
}