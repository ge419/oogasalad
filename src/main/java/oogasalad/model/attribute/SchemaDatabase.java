package oogasalad.model.attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import java.io.File;
import java.io.IOException;
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

/**
 * This utility class hold all metaData and is useful for determining the parameter type that a
 * BMetaData takes or an
 */
@Singleton
public class SchemaDatabase {

  public static final String SCHEMA_RESOURCE_PATH = "schemas";
  private static final Logger logger = LogManager.getLogger(SchemaDatabase.class);
  private final Map<String, ObjectSchema> resourceSchemaMap;
  private final MapProperty<String, ObjectSchema> generatedSchemaMapProperty;
  private final ListProperty<Rule> ruleListProperty;


  public SchemaDatabase() {
    resourceSchemaMap = new HashMap<>();
    generatedSchemaMapProperty = new SimpleMapProperty<>(FXCollections.observableHashMap());
    ruleListProperty = new SimpleListProperty<>();
    readResourceSchemaFiles();
    generateSchemas();
  }

  public Optional<ObjectSchema> getSchema(String name) {
    return Optional.ofNullable(generatedSchemaMapProperty.get().get(name));
  }

  public boolean containsSchema(String name) {
    return generatedSchemaMapProperty.get().containsKey(name);
  }

  public List<String> getAllSchemaNames() {
    return generatedSchemaMapProperty.get().keySet().stream().toList();
  }

  public MapProperty<String, ObjectSchema> databaseProperty() {
    return generatedSchemaMapProperty;
  }

  public void setRuleListProperty(ObservableList<Rule> ruleListProperty) {
    this.ruleListProperty.set(ruleListProperty);
    this.ruleListProperty.addListener((observable, oldValue, newValue) -> generateSchemas());
    for (Rule rule : this.ruleListProperty) {
      rule.appliedSchemasProperty()
          .addListener((observable, oldValue, newValue) -> generateSchemas());
    }

    generateSchemas();
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
    SimpleObjectSchema schema = mapper.readValue(file, SimpleObjectSchema.class);
    String schemaName = schema.getName();

    if (resourceSchemaMap.containsKey(schemaName)) {
      logger.error("Duplicate schema name {}", schemaName);
    }
    resourceSchemaMap.put(schemaName, schema);
  }

  private void generateSchemas() {
    Map<String, Set<String>> boundSchemaMap = mapSchemasToResources();
    Map<String, ObjectSchema> generatedSchemaMap = new HashMap<>();

    for (Entry<String, Set<String>> entry : boundSchemaMap.entrySet()) {
      List<ObjectSchema> appliedSchemas =
          entry.getValue().stream().map(resourceSchemaMap::get).toList();

      ObjectSchema schema = SchemaUtilities.concatenateSchemas(entry.getKey(), appliedSchemas);
      generatedSchemaMap.put(entry.getKey(), schema);
    }

    generatedSchemaMapProperty.set(FXCollections.observableMap(generatedSchemaMap));
  }

  private Map<String, Set<String>> mapSchemasToResources() {
    Map<String, Set<String>> schemasToResources = new HashMap<>();

    // Resource schemas are always present
    for (String resourceSchema : resourceSchemaMap.keySet()) {
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
