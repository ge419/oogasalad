package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class TileListAttribute extends AbstractAttribute {

  @JsonProperty
  private ListProperty<String> tileIds;

  @JsonCreator
  public TileListAttribute(@JsonProperty("key") String key,
      @JsonProperty("ids") List<String> tileIds) {
    super(key);
    this.tileIds = new SimpleListProperty<>(FXCollections.observableList(tileIds));
  }

  public static TileListAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, TileListAttribute.class);
  }

  @JsonGetter("ids")
  public List<String> getTileIds() {
    return tileIds;
  }

  @JsonSetter("ids")
  public void setTileIds(List<String> tileIds) {
    this.tileIds.setAll(tileIds);
  }

  public ListProperty<String> tileIdsProperty() {
    return tileIds;
  }

  @Override
  public String toString() {
    return String.format("Tile list {value: %s}", this.tileIds);
  }
}
