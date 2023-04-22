package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;

public class TileAttribute extends StringAttribute {
  @JsonCreator
  protected TileAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key, value);
  }

  public static TileAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, TileAttribute.class);
  }

  public String getId() {
    return this.getValue();
  }

  public void setId(String newId) {
    this.setValue(newId);
  }

}

