/**
 * An API for building dynamic, type-safe, serializable data.
 *
 * <p>An {@link oogasalad.model.attribute.AbstractAttribute} is a single key/value pair, with a
 * value defined by the concrete class. {@link oogasalad.model.attribute.AbstractMetadata} describes
 * editing information for a field, such as if it can be edited and min/max values.
 * {@link oogasalad.model.attribute.SimpleObjectSchema} defines all the fields a type has and how to
 * edit them, and allows generation of the appropriate attributes.
 */
package oogasalad.model.attribute;