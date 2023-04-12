package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntAttribute extends Attribute {
  private final IntegerProperty value;

  @JsonCreator
  public IntAttribute(@JsonProperty("key") String key, @JsonProperty("value") int value) {
    super(key);
    this.value = new SimpleIntegerProperty(value);
  }

  public static IntAttribute from(Attribute attr) {
    return oogasalad.model.attribute.Attribute.getAs(attr, IntAttribute.class);
  }

  public int getValue() {
    return value.get();
  }

  public IntegerProperty valueProperty() {
    return value;
  }

  public void setValue(int value) {
    this.value.set(value);
  }

  @Override
  public String toString() {
    return "IntAttribute{" +
        "value=" + value +
        '}';
  }
}
