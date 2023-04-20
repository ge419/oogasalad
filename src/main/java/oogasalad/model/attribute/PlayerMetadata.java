package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerMetadata extends AbstractMetadata{

  public static final Class<PlayerAttribute> ATTRIBUTE_CLASS = PlayerAttribute.class;

  @JsonCreator
  public PlayerMetadata(@JsonProperty("key") String key) {
    super(key);
  }
  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    return false;
  }

  @Override
  public Attribute makeAttribute() {
    return null;
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }
  public PlayerAttribute makePlayerAttribute() {
    return new PlayerAttribute(getKey(), "");
  }

}
