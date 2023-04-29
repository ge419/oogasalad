package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringAttribute extends AbstractAttribute {

  private final StringProperty value;

  @JsonCreator
  public StringAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key);
    this.value = new SimpleStringProperty(value);
  }

  public static StringAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, StringAttribute.class);
  }

  public String getValue() {
    return value.get();
  }

  public void setValue(String value) {
    this.value.set(value);
  }

  public StringProperty valueProperty() {
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
    StringAttribute that = (StringAttribute) o;
    boolean key = this.getKey().equals(that.getKey());
    boolean value = this.value.get().equals(that.value.get());
    return key && value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey()+value.get());
  }

  @Override
  public String toString() {
    return String.format("String Attribute {value: %s}", this.value);
  }
}
