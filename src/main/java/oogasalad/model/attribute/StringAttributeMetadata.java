package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringAttributeMetadata extends AttributeMetadata {

  private final StringProperty defaultValue;

  @JsonCreator
  public StringAttributeMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleStringProperty("");
  }

  @Override
  public Attribute makeAttribute() {
    return makeStringAttribute();
  }

  public StringAttribute makeStringAttribute() {
    return new StringAttribute(getKey(), getDefaultValue());
  }

  public String getDefaultValue() {
    return defaultValue.get();
  }

  public StringProperty defaultValueProperty() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue.set(defaultValue);
  }

  @Override
  public String toString() {
    return "StringAttributeMetadata{" +
        "defaultValue=" + defaultValue +
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
    if (!super.equals(o)) {
      return false;
    }
    StringAttributeMetadata that = (StringAttributeMetadata) o;
    return Objects.equals(getDefaultValue(), that.getDefaultValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultValue);
  }
}
