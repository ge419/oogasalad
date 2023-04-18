/**
 * An API for building dynamic, type-safe, serializable data.
 *
 * <p>An {@link oogasalad.model.attribute.Attribute} is a single key/value pair, with a value
 * defined by the concrete class. {@link oogasalad.model.attribute.Metadata} describes editing
 * information for a field, such as if it can be edited and min/max values.
 * {@link oogasalad.model.attribute.ObjectSchema} defines all the fields a type has and how to edit
 * them, and allows generation of the appropriate
 */
package oogasalad.model.attribute;