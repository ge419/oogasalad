package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TileAttribute extends Attribute {
  private final IntegerProperty value;

  @JsonCreator
  public TileAttribute(@JsonProperty("key") String key, @JsonProperty("value") Integer value) {
    super(key);
    this.value = new SimpleIntegerProperty(value);
  }

  public static TileAttribute from(Attribute attr) {
    return Attribute.getAs(attr, TileAttribute.class);
  }

  public int getValue() {
    return this.value.get();
  }

  public void setValue(int value) {
    this.value.set(value);
  }

  @Override
  public String toString() {
    return "TileAttribute{" +
        "value=" + value +
        '}';
  }
}

