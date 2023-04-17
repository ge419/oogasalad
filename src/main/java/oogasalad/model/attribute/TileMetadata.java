package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TileMetadata extends AbstractMetadata {

  public static final Class<TileAttribute> ATTRIBUTE_CLASS = TileAttribute.class;

  @JsonCreator
  public TileMetadata(@JsonProperty("key") String key) {
    super(key);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    String id = TileAttribute.from(attribute).getId();
    return isValidTileId(id);
  }

  public boolean isValidTileId(String id) {
    // No preconditions
    return true;
  }

  @Override
  public Attribute makeAttribute() {
    return makeTileAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public static TileMetadata from(Metadata meta) {
    return getAs(meta, TileMetadata.class);
  }

  public TileAttribute makeTileAttribute() {
    return new TileAttribute(getKey(), "");
  }
}
