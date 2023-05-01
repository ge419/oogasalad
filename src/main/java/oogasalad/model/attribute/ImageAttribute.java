package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageAttribute extends StringAttribute {

  @JsonCreator
  protected ImageAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key, value);
  }

  public static ImageAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, ImageAttribute.class);
  }

}
