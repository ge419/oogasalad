package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerAttribute extends Attribute {

  private final StringProperty id;
  public PlayerAttribute(@JsonProperty("key") String key, @JsonProperty("id") String id) {
    super(key);
    this.id = new SimpleStringProperty(id);
  }

  public static PlayerAttribute from(Attribute attr) {
    return Attribute.getAs(attr, PlayerAttribute.class);
  }

  public String getId() {
    return this.id.get();
  }

  public void setId(String id) {
    this.id.set(id);
  }

  @Override
  public String toString() {
    return String.format("Player Attribute {value: %s}", this.id);
  }
}
