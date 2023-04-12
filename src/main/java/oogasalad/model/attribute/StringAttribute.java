package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class StringAttribute extends Attribute {
  private final StringProperty value;

  @JsonCreator
  public StringAttribute(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    super(key);
    this.value = new SimpleStringProperty(value);
  }

  public static StringAttribute from(Attribute attr) {
    return Attribute.getAs(attr, StringAttribute.class);
  }

  public String getValue() {
    return value.get();
  }

  public StringProperty valueProperty() {
    return value;
  }

  public void setValue(String value) {
    this.value.set(value);
  }

  @Override
  public String toString() {
    return "StringAttribute{" +
        "value=" + value +
        '}';
  }
}
