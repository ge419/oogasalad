package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntAttribute extends AbstractAttribute {

  private final IntegerProperty value;

  @JsonCreator
  public IntAttribute(@JsonProperty("key") String key, @JsonProperty("value") int value) {
    super(key);
    this.value = new SimpleIntegerProperty(value);
  }

  public static IntAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, IntAttribute.class);
  }

  public int getValue() {
    return value.get();
  }

  public void setValue(int value) {
    this.value.set(value);
  }

  public IntegerProperty valueProperty() {
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
    IntAttribute that = (IntAttribute) o;
    boolean key = this.getKey().equals(that.getKey());
    boolean value = this.value.get()==that.value.get();
    return key && value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey()+value.get());
  }
}
