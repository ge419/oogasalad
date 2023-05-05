package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class PlayerListMetadata extends AbstractMetadata {

  public static final Class<PlayerListAttribute> ATTRIBUTE_CLASS = PlayerListAttribute.class;

  @JsonCreator
  protected PlayerListMetadata(@JsonProperty("key") String key) {
    super(key);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    //No preconditions
    return true;
  }

  @Override
  public Attribute makeAttribute() {
    return makePlayerListAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public PlayerListAttribute makePlayerListAttribute() {
    ArrayList<String> str = new ArrayList<>();
    return new PlayerListAttribute(getKey(), str);
  }
}
