package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DoubleAttribute extends Attribute {

  private final DoubleProperty value;

  @JsonCreator
  public DoubleAttribute(@JsonProperty("key") String key, @JsonProperty("value") double value) {
    super(key);
    this.value = new SimpleDoubleProperty(value);
  }

  public static DoubleAttribute from(Attribute attr) {
    return Attribute.getAs(attr, DoubleAttribute.class);
  }

  public double getValue() {
    return value.get();
  }

  public void setValue(double value) {
    this.value.set(value);
  }

  public DoubleProperty valueProperty() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DoubleAttribute that = (DoubleAttribute) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
