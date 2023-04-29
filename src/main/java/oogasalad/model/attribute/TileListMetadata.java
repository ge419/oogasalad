package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class TileListMetadata extends AbstractMetadata {

  public static final Class<TileListAttribute> ATTRIBUTE_CLASS = TileListAttribute.class;

  @JsonCreator
  public TileListMetadata(@JsonProperty("key") String key) {
    super(key);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    List<String> val = TileListAttribute.from(attribute).getTileIds();
    return isValidTileIds(val);
  }

  public boolean isValidTileIds(List<String> val) {
    // No preconditions
    return true;
  }

  @Override
  public Attribute makeAttribute() {
    return makeTileListAttribute();
  }

  @Override
  @JsonIgnore
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public static TileListMetadata from(Metadata meta) {
    return getAs(meta, TileListMetadata.class);
  }

  public TileListAttribute makeTileListAttribute() {
    ArrayList<String> str = new ArrayList<>();
    return new TileListAttribute(getKey(), str);
  }
}
