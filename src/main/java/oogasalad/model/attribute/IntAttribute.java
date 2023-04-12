package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
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
    return Attribute.getAs(attr, IntAttribute.class);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IntAttribute that = (IntAttribute) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
