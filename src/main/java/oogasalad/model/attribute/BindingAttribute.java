package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Indicates a string used to bind to a schema.
 *
 * @author Dominic Martinez
 */
public class BindingAttribute extends StringAttribute {

  @JsonCreator
  protected BindingAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key, value);
  }

  public static BindingAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, BindingAttribute.class);
  }
}
