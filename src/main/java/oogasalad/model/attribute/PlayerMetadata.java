package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerMetadata extends AbstractMetadata {

  public static final Class<PlayerAttribute> ATTRIBUTE_CLASS = PlayerAttribute.class;
  public static final String DEFAULT_ID = "";

  @JsonCreator
  public PlayerMetadata(@JsonProperty("key") String key) {
    super(key);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    //no preconditions
    return true;
  }

  @Override
  public Attribute makeAttribute() {
    return makePlayerAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public PlayerAttribute makePlayerAttribute() {
    return new PlayerAttribute(getKey(), DEFAULT_ID);
  }

}
