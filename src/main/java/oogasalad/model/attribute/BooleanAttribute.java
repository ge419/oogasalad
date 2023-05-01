package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BooleanAttribute extends AbstractAttribute {

  private final BooleanProperty value;

  @JsonCreator
  protected BooleanAttribute(@JsonProperty("key") String key,
      @JsonProperty("value") boolean value) {
    super(key);
    this.value = new SimpleBooleanProperty(value);
  }

  public static BooleanAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, BooleanAttribute.class);
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
    boolean key = this.getKey().equals(that.getKey());
    boolean value = this.value.get() == that.value.get();
    return key && value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey() + value.get());
  }
}
