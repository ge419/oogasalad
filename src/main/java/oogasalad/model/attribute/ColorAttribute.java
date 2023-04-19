package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorAttribute extends StringAttribute{

  @JsonCreator
  protected ColorAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key, value);
  }

}
