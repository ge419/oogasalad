package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Represents a key-value pair to store a piece of information. The value cannot be accessed via the
 * interface. Consumers are required to know the attribute type from the {@link ObjectSchema} and
 * cast the attribute with the appropriate static {@code from} method, e.g.
 * {@link IntAttribute#from(Attribute)}.
 *
 * @author Dominic Martinez
 * @see Metadata
 * @see ObjectSchema
 */
@JsonTypeInfo(use = Id.CLASS)
public interface Attribute {

  /**
   * @return the key for this attribute.
   */
  String getKey();
}
