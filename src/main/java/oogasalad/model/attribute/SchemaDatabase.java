package oogasalad.model.attribute;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.MapProperty;
import javafx.collections.ObservableList;
import oogasalad.model.engine.rules.Rule;

/**
 * Holds the available {@link oogasalad.model.constructable.GameConstruct} schemas. Consumers are
 * expected to subscribe to the {@link #databaseProperty()} and listen for changes in schemas.
 *
 * @author Dominic Martinez, Jay Yoon
 */
public interface SchemaDatabase {

  /**
   * Get a schema from the database if it exists.
   *
   * @param name Name of schema
   * @return The schema if it exists, {@link Optional#empty()} otherwise
   */
  Optional<ObjectSchema> getSchema(String name);

  /**
   * Returns true if {@link #getSchema(String)} would return a schema, false otherwise.
   */
  boolean containsSchema(String name);

  /**
   * This property is updated when schemas are added/removed, or an existing schema is updated.
   *
   * @return Property mapping schema names to their schema object.
   */
  MapProperty<String, ObjectSchema> databaseProperty();

  /**
   * Sets the rule list property to listen to. Rules are used to bind schema names together, so
   * setting this property is required for the database to update properly.
   *
   * @param ruleListProperty Property list of global rules
   * @see Rule#appliedSchemasProperty()
   */
  void setRuleListProperty(ObservableList<Rule> ruleListProperty);

  /**
   * Add a schema to the database.
   *
   * @param schema schema to be added
   */
  void addCustomSchema(ObjectSchema schema);

  /**
   * Get the type of the provided schema name.
   *
   * @param schemaName name to get type for
   * @return type of schema
   * @see SchemaType
   */
  SchemaType getSchemaType(String schemaName);

  /**
   * Returns the schemas added during runtime, <em>not</em> from a resource file. These schemas are
   * expected to be serialized.
   */
  List<ObjectSchema> getCustomSchemas();

  /**
   * Reads a file containing a list of {@link ObjectSchema}, and adds them to the database.
   *
   * @param path file to read
   * @return
   */
  List<ObjectSchema> readSchemaListFile(Path path) throws IOException;

  /**
   * Reads a file containing a single {@link ObjectSchema}, and adds it to the database.
   *
   * @param path file to read
   * @return
   */
  ObjectSchema readSchemaFile(Path path) throws IOException;
}
