package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameConstructAttribute extends AbstractAttribute {

  private final StringProperty id;

  @JsonCreator
  public GameConstructAttribute(@JsonProperty("key") String key, @JsonProperty("id") String id) {
    super(key);
    this.id = new SimpleStringProperty(id);
  }

  public static TileAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, TileAttribute.class);
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

