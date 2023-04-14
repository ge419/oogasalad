package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class TileListMetadata extends Metadata {

  public static final Class<TileListAttribute> ATTRIBUTE_CLASS = TileListAttribute.class;

  @JsonCreator
  protected TileListMetadata(@JsonProperty("key") String key) {
    super(key);
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

  public TileListAttribute makeTileListAttribute() {
    ArrayList<String> str = new ArrayList<>();
    return new TileListAttribute(getKey(), str);
  }
}
