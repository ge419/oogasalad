package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerAttribute extends GameConstructAttribute {

  public PlayerAttribute(String key, String id) {
    super(key, id);
  }
}
