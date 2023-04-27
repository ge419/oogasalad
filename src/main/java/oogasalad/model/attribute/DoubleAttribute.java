package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DoubleAttribute extends AbstractAttribute {

  private final DoubleProperty value;

  @JsonCreator
  public DoubleAttribute(@JsonProperty("key") String key, @JsonProperty("value") double value) {
    super(key);
    this.value = new SimpleDoubleProperty(value);
  }

  public static DoubleAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, DoubleAttribute.class);
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
    boolean value = this.value.get() == that.value.get();
    boolean key = this.getKey().equals(that.getKey());
    return value && key;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey()+value.get());
  }
}
