package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringAttribute extends Attribute {

  private final StringProperty value;

  @JsonCreator
  protected StringAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key);
    this.value = new SimpleStringProperty(value);
  }

  public static StringAttribute from(Attribute attr) {
    return Attribute.getAs(attr, StringAttribute.class);
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
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return String.format("String Attribute {value: %s}", this.value);
  }
}
