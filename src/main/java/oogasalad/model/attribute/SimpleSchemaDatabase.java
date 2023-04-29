package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.exception.FileReaderException;
import oogasalad.model.exception.ResourceReadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Singleton
public class SimpleSchemaDatabase implements SchemaDatabase {

  public static final String SCHEMA_RESOURCE_PATH = "schemas";
  private static final Logger LOGGER = LogManager.getLogger(SimpleSchemaDatabase.class);
  private final Map<String, ObjectSchema> baseSchemaMap;
  private final MapProperty<String, ObjectSchema> generatedSchemaMapProperty;
  private final ListProperty<Rule> ruleListProperty;
  private final ObjectMapper mapper;
  private final Set<String> customSchemaKeys;


  /**
   * Create a schema database without using an object mapper that supports dependency injection.
   *
   * @deprecated in favor of DI constructor
   */
  @Deprecated(forRemoval = true)
  public SimpleSchemaDatabase() {
    this(new ObjectMapper());
  }

  @Inject
  public SimpleSchemaDatabase(ObjectMapper mapper) {
    this.mapper = mapper;
    baseSchemaMap = new HashMap<>();
    generatedSchemaMapProperty = new SimpleMapProperty<>(FXCollections.observableHashMap());
    ruleListProperty = new SimpleListProperty<>();
    customSchemaKeys = new HashSet<>();
    readResourceSchemaFiles();
    generateSchemas();
  }

  @Override
  public Optional<ObjectSchema> getSchema(String name) {
    return Optional.ofNullable(generatedSchemaMapProperty.get().get(name));
  }

  @Override
  public boolean containsSchema(String name) {
    return generatedSchemaMapProperty.get().containsKey(name);
  }

  @Override
  public MapProperty<String, ObjectSchema> databaseProperty() {
    return generatedSchemaMapProperty;
  }

  @Override
  @JsonIgnore
  public void setRuleListProperty(ObservableList<Rule> ruleListProperty) {
    this.ruleListProperty.set(ruleListProperty);
    this.ruleListProperty.addListener((observable, oldValue, newValue) -> generateSchemas());
    for (Rule rule : this.ruleListProperty) {
      rule.appliedSchemasProperty()
          .addListener((observable, oldValue, newValue) -> generateSchemas());
    }

    generateSchemas();
  }

  @Override
  public void addCustomSchema(ObjectSchema schema) {
    addSchema(schema);
    customSchemaKeys.add(schema.getName());
  }

  @Override
  public List<ObjectSchema> getCustomSchemas() {
    return customSchemaKeys.stream().map(baseSchemaMap::get).toList();
  }

  @Override
  public List<ObjectSchema> readSchemaListFile(Path path) throws IOException {
    return mapper.readValue(path.toFile(), new TypeReference<>() {
    });
  }

  @Override
  public ObjectSchema readSchemaFile(Path path) throws IOException {
    return mapper.readValue(path.toFile(), ObjectSchema.class);
  }

  private void addSchema(ObjectSchema schema) {
    String schemaName = schema.getName();

    if (baseSchemaMap.containsKey(schemaName)) {
      if (customSchemaKeys.contains(schemaName)) {
        LOGGER.info("Replacing schema name {}", schemaName);
      } else {
        LOGGER.error("Conflict with resource schema {}", schemaName);
        throw new IllegalArgumentException("invalid schema key");
      }
    }

    baseSchemaMap.put(schemaName, schema);
    generateSchemas();
  }

  private void readResourceSchemaFiles() {
    try {
      for (File file : FileReader.readFiles(SCHEMA_RESOURCE_PATH)) {
        addSchema(readSchemaFile(file.toPath()));
      }
    } catch (IOException | FileReaderException e) {
      LOGGER.fatal("Failed to read resource schema file", e);
      throw new ResourceReadException(e);
    }
  }

  private void generateSchemas() {
    Map<String, Set<String>> boundSchemaMap = mapSchemasToResources();
    Map<String, ObjectSchema> generatedSchemaMap = new HashMap<>();

    for (Entry<String, Set<String>> entry : boundSchemaMap.entrySet()) {
      List<ObjectSchema> appliedSchemas =
          entry.getValue().stream().map(baseSchemaMap::get).toList();

      ObjectSchema schema = SchemaUtilities.concatenateSchemas(entry.getKey(), appliedSchemas);
      generatedSchemaMap.put(entry.getKey(), schema);
    }

    generatedSchemaMapProperty.set(FXCollections.observableMap(generatedSchemaMap));
  }

  private Map<String, Set<String>> mapSchemasToResources() {
    Map<String, Set<String>> schemasToResources = new HashMap<>();

    // Resource schemas are always present
    for (String resourceSchema : baseSchemaMap.keySet()) {
      schemasToResources.put(resourceSchema, new HashSet<>(List.of(resourceSchema)));
    }

    // Bind sources to sinks
    for (Rule rule : ruleListProperty) {
      for (SchemaBinding binding : rule.getAppliedSchemas()) {
        if (!schemasToResources.containsKey(binding.sink())) {
          schemasToResources.put(binding.sink(), new HashSet<>());
        }

        schemasToResources.get(binding.sink()).add(binding.source());
      }
    }

    // Traverse intermediaries
    for (String key : schemasToResources.keySet()) {
      traverseKey(key, schemasToResources);
    }

    return schemasToResources;
  }

  private void traverseKey(String key, Map<String, Set<String>> schemasToResources) {
    for (String childKey : schemasToResources.get(key)) {
      if (childKey.equals(key)) {
        continue;
      }

      traverseKey(childKey, schemasToResources);
      schemasToResources.get(key).addAll(schemasToResources.get(childKey));
    }
  }
}
