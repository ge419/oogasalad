package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TileAttribute extends Attribute {
  private final StringProperty id;

  @JsonCreator
  public TileAttribute(@JsonProperty("key") String key, @JsonProperty("id") String id) {
    super(key);
    this.id = new SimpleStringProperty(id);
  }

  public static TileAttribute from(Attribute attr) {
    return Attribute.getAs(attr, TileAttribute.class);
  }

  public String getId() {
    return this.id.get();
  }

  public void setId(String id) {
    this.id.set(id);
  }

  @Override
  public String toString() {
    return String.format("Tile Attribute {value: %s}", this.id);
  }
}

