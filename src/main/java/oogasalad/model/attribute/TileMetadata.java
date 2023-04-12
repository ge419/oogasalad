package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TileMetadata extends Metadata {

  public static final Class<TileAttribute> ATTRIBUTE_CLASS = TileAttribute.class;

  @JsonCreator
  public TileMetadata(@JsonProperty("key") String key) {
    super(key);
  }

  @Override
  public Attribute makeAttribute() {
    return makeTileAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }


  public TileAttribute makeTileAttribute() {
    return new TileAttribute(getKey(), "");
  }
}
