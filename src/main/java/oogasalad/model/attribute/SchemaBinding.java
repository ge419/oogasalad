package oogasalad.model.attribute;

/**
 * Defines a binding between two schemas, such that the source schema always appears when the sink
 * schema is requested.
 *
 * @param sink   schema that should be added to
 * @param source the schema to add onto the sink
 */
public record SchemaBinding(String sink, String source) {

}
