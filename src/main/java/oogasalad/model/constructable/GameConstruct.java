package oogasalad.model.constructable;

import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectProperty;
import oogasalad.model.attribute.AbstractAttribute;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.SimpleObjectSchema;

/**
 * Represents a game-specific object. {@link GameConstruct}s are:
 * <ul>
 *   <li>Editable by the user</li>
 *   <li>Serializable and deserializable</li>
 * </ul>
 *
 * <p>A {@link GameConstruct} consists of a {@link SimpleObjectSchema} and associated {@link AbstractAttribute}s.
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
 * @see SimpleObjectSchema
 * @see AbstractAttribute
 */
public interface GameConstruct {

  /**
   * Returns the unique identifier for this {@link GameConstruct}.
   */
  String getId();

  /**
   * Returns the attribute associated with a key. Keys present in the current schema will always
   * exist.
   *
   * @param key appropriate key from the {@link SimpleObjectSchema}
   * @return an {@link Optional#of(Object)} if the key exists, {@link Optional#empty()} otherwise
   */
  Optional<Attribute> getAttribute(String key);

  /**
   * Returns the current schema for this {@link GameConstruct}. Note that the schema can change at
   * any point; make sure you really want this method instead of
   * {@link GameConstruct#getSchemaProperty()}.
   */
  SimpleObjectSchema getSchema();

  /**
   * Returns a property for this object's {@link SimpleObjectSchema}.
   */
  ReadOnlyObjectProperty<ObjectSchema> getSchemaProperty();
}
