package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BooleanAttribute extends Attribute {

  private final BooleanProperty value;

  @JsonCreator
  protected BooleanAttribute(@JsonProperty("key") String key,
      @JsonProperty("value") boolean value) {
    super(key);
    this.value = new SimpleBooleanProperty(value);
  }

  public static BooleanAttribute from(Attribute attr) {
    return Attribute.getAs(attr, BooleanAttribute.class);
  }

  public boolean getValue() {
    return value.get();
  }

  public void setValue(boolean value) {
    this.value.set(value);
  }

  public BooleanProperty valueProperty() {
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
    BooleanAttribute that = (BooleanAttribute) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
