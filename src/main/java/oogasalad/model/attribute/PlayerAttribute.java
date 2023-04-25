package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerAttribute extends GameConstructAttribute {

  @JsonCreator
  public PlayerAttribute(@JsonProperty("key") String key, @JsonProperty("id") String id) {
    super(key, id);
  }

  public static PlayerAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, PlayerAttribute.class);
  }
}
