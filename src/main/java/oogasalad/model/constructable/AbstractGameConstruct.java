package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import oogasalad.model.attribute.SchemaType;
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
  private List<String> schemaNames;
  private final String baseSchema;
  private String id;
  private final ObjectProperty<ObjectSchema> schemaProperty;

  protected AbstractGameConstruct(String baseSchema, SchemaDatabase database) {
    this.id = UUID.randomUUID().toString();
    this.database = database;
    this.attributeMap = new TreeMap<>();
    this.schemaProperty = new SimpleObjectProperty<>();
    this.baseSchema = baseSchema;

    database.databaseProperty().addListener((observable, oldValue, newValue) -> refreshSchema());
    setSchemaNames(List.of(baseSchema));
    setAttributeListeners();
  }

  /**
   * Get a unique ID identifying this construct
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Set the unique ID that identifies this construct
   *
   * @param id string ID to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns a list of all the attributes present on this construct
   */
  @JsonGetter("attributes")
  public List<Attribute> getAllAttributes() {
    return attributeMap.values().stream().toList();
  }

  /**
   * Set all the attributes on this construct. Note the following caveats:
   * <p>
   *   <ul>Doing this clears all current listeners; use with caution</ul>
   *   <ul>Attributes that conflict with the schema will be ignored</ul>
   * </p>
   *
   * @param attributeList set the attributes for this construct
   */
  @JsonSetter("attributes")
  public void setAllAttributes(List<Attribute> attributeList) {
    for (Attribute attribute : attributeList) {
      Objects.requireNonNull(attribute);
      attributeMap.put(attribute.getKey(), attribute);
    }

    reconcileAttributes(getSchema());
    setAttributeListeners();
  }

  @Override
  @JsonIgnore
  public Optional<Attribute> getAttribute(String key) {
    return Optional.ofNullable(attributeMap.get(key));
  }

  /**
   * Get the list of schemas applied to this construct.
   */
  @JsonGetter("schemas")
  public List<String> getSchemaNames() {
    return Collections.unmodifiableList(schemaNames);
  }

  @Override
  @JsonSetter("schemas")
  public void setSchemaNames(List<String> newSchemaNames) {
    // Ensure base schema is covered
    List<String> newNames = new ArrayList<>(newSchemaNames);
    newNames.add(baseSchema);

    List<ObjectSchema> schemas = new ArrayList<>();
    for (String newSchemaName : newNames.stream().distinct().toList()) {
      if (newSchemaName.isEmpty()) {
        continue;
      }

      Optional<ObjectSchema> schemaOptional = database.getSchema(newSchemaName);
      if (schemaOptional.isEmpty()) {
        LOGGER.warn("schema does not exist {}", newSchemaName);
      } else {
        schemas.add(schemaOptional.get());
      }
    }

    this.schemaNames = new ArrayList<>(newSchemaNames);
    ObjectSchema newSchema = SchemaUtilities.concatenateSchemas(schemas);
    reconcileAttributes(newSchema);

    this.schemaProperty.setValue(newSchema);
  }

  /**
   * Notifies inheritors when they should refresh their attribute listeners
   */
  protected void setAttributeListeners() {
    // Does nothing by default
  }

  /**
   * Set schema names, preserving any custom schemas that were added
   *
   * @param newSchemaNames schema names to set for built-in schemas
   */
  protected void setBuiltinSchemas(List<String> newSchemaNames) {
    List<String> schemas = Streams.concat(
            getSchemaNames().stream()
                .filter(schema -> database.getSchemaType(schema) == SchemaType.CUSTOM),
            newSchemaNames.stream())
        .toList();

    setSchemaNames(schemas);
  }

  @Override
  @JsonIgnore
  public ObjectSchema getSchema() {
    return schemaProperty.get();
  }

  @Override
  @JsonIgnore
  public ReadOnlyObjectProperty<ObjectSchema> schemaProperty() {
    return schemaProperty;
  }

  @Override
  public void addSchema(String schemaName) {
    this.schemaNames.add(schemaName);
    refreshSchema();
  }

  @Override
  public void addSchema(ObjectSchema schema) {
    if (!database.containsSchema(schema.getName())) {
      database.addCustomSchema(schema);
    }

    this.schemaNames.add(schema.getName());
    refreshSchema();
  }

  private void refreshSchema() {
    setSchemaNames(schemaNames);
  }

  /**
   * Reconcile current attributes with the current schema, migrating attributes when possible. This
   * purposely keeps old attributes in the map so that deleted attributes retain their value if
   * added back.
   * TODO: add a mechanism to delete unused attributes before serialization?
   */
  private void reconcileAttributes(ObjectSchema schema) {
    for (Metadata metadata : schema.getAllMetadata()) {
      String key = metadata.getKey();
      boolean canKeepAttribute = attributeMap.containsKey(key)
          && canMigrateAttribute(attributeMap.get(key), metadata);
      if (!canKeepAttribute) {
        Attribute attr = metadata.makeAttribute();
        if (attr == null) {
          LOGGER.fatal("metadata returned null attribute for key {}", key);
          throw new IllegalStateException("bad attribute from metadata");
        }

        attributeMap.put(key, attr);
      }
    }
  }

  /**
   * Returns true if an attribute can be migrated to a metadata field, false otherwise.
   */
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
}