package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.List;

public class TileListAttribute extends Attribute {

  @JsonProperty
  private List<String> tileIds;

  @JsonCreator
  public TileListAttribute(@JsonProperty("key") String key,
      @JsonProperty("ids") List<String> tileIds) {
    super(key);
    this.tileIds = new ArrayList<>(tileIds);
  }

  public static TileListAttribute from(Attribute attr) {
    return Attribute.getAs(attr, TileListAttribute.class);
  }

  @JsonGetter("ids")
  public List<String> getTileIds() {
    return tileIds;
  }

  @JsonSetter("ids")
  public void setTileIds(List<String> tileIds) {
    this.tileIds = tileIds;
  }

  public List<String> tileIdsProperty() {
    return tileIds;
  }

  @Override
  public String toString() {
    return String.format("Tile list {value: %s}", this.tileIds);
  }
}
