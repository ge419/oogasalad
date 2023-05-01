package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerAttribute extends GameConstructAttribute {

  private final StringProperty id;

  @JsonCreator
  public PlayerAttribute(@JsonProperty("key") String key, @JsonProperty("id") String id) {
    super(key, id);
    this.id = new SimpleStringProperty(id);
  }

  public static PlayerAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, PlayerAttribute.class);
  }

  public String getIdValue() {
    return id.get();
  }

  public void setIdValue(String playerId) {
    id.setValue(playerId);
  }
}
