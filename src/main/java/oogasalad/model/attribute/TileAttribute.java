package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TileAttribute extends GameConstructAttribute {

  @JsonCreator
  public TileAttribute(@JsonProperty("key") String key, @JsonProperty("id") String id) {
    super(key, id);
  }

  public static TileAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, TileAttribute.class);
  }

}

