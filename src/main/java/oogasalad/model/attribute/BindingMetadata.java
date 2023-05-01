package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Indicates a string used to bind to a schema.
 *
 * @author Dominic Martinez
 */
public class BindingMetadata extends StringMetadata {

  @JsonCreator
  public BindingMetadata(@JsonProperty("key") String key) {
    super(key);
  }

  @Override
  public Attribute makeAttribute() {
    return makeBindingAttribute();
  }

  public BindingAttribute makeBindingAttribute() {
    return new BindingAttribute(getKey(), getDefaultValue());
  }
}
