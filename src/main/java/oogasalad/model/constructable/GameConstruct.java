package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
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
 * to listen to {@link GameConstruct#getSchemaProperty()} for changes.
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
  Attribute getAttribute(String key);

  /**
   * Returns the current schema for this {@link GameConstruct}. Note that the schema can change at
   * any point; make sure you really want this method instead of
   * {@link GameConstruct#getSchemaProperty()}.
   */
  ObjectSchema getSchema();

  /**
   * Returns a property for this object's {@link ObjectSchema}.
   */
  ReadOnlyObjectProperty<ObjectSchema> getSchemaProperty();
}
