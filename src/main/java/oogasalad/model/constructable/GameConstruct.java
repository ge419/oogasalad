package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectProperty;
import oogasalad.model.attribute.AbstractAttribute;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.ObjectSchema;

/**
 * Represents a game-specific object. {@link GameConstruct}s are:
 * <ul>
 *   <li>Editable by the user</li>
 *   <li>Serializable and deserializable</li>
 * </ul>
 *
 * <p>A {@link GameConstruct} consists of a {@link ObjectSchema} and associated {@link AbstractAttribute}s.
 * The schema is a concatenation of:
 * <ul>
 *   <li>The base schema for this object type</li>
 *   <li>Factory attributes that determine the object specialization</li>
 *   <li>Attributes related to the object specialization</li>
 * </ul>
 * The available attributes are guaranteed to match up with the current schema, but the schema
 * may change at any time due to a change in the factory attributes or a
 * {@link oogasalad.model.engine.rules.Rule}. Objects operating on general schemas are expected
 * to listen to {@link GameConstruct#schemaProperty()} for changes.
 *
 * @author Dominic Martinez
 * @see ObjectSchema
 * @see AbstractAttribute
 */
@JsonTypeInfo(use = Id.CLASS)
public interface GameConstruct {

  /**
   * Returns the unique identifier for this {@link GameConstruct}.
   */
  String getId();

  /**
   * Returns the attribute associated with a key. Keys present in the current schema will always
   * exist.
   *
   * @param key appropriate key from the {@link ObjectSchema}
   * @return an {@link Attribute} if the key exists, null otherwise
   */
  Optional<Attribute> getAttribute(String key);

  /**
   * Returns the current schema for this {@link GameConstruct}. Note that the schema can change at
   * any point; make sure you really want this method instead of
   * {@link GameConstruct#schemaProperty()}.
   */
  @JsonIgnore
  ObjectSchema getSchema();

  /**
   * Returns a property for this object's {@link ObjectSchema}.
   */
  ReadOnlyObjectProperty<ObjectSchema> schemaProperty();

  /**
   * Get a list of schema names for this construct.
   */
  List<String> getSchemaNames();

  /**
   * Sets the schema names for this construct. The construct may add on default schemas to this list
   * if not already present.
   *
   * @param schemaNames new schema names to set
   */
  void setSchemaNames(List<String> schemaNames);

  /**
   * Adds a schema to the schemas applied to this object.
   *
   * @param schemaName name of schema to add that exists in the database
   */
  void addSchema(String schemaName);

  /**
   * Adds a schema to the schemas applied to this object.
   *
   * @param schema schema to add, need not be in database
   */
  void addSchema(ObjectSchema schema);
}
