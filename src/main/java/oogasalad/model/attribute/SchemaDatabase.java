package oogasalad.model.attribute;

import com.google.inject.Singleton;
import java.util.Optional;
import javafx.beans.property.MapProperty;
import javafx.collections.ObservableList;
import oogasalad.model.engine.rules.Rule;

/**
 * Holds the available {@link oogasalad.model.constructable.GameConstruct} schemas. Consumers are
 * expected to subscribe to the {@link #databaseProperty()} and listen for changes in schemas.
 *
 * @author Dominic Martinez
 */
@Singleton
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
}
