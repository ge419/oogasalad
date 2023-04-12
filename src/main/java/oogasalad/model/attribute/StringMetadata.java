package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringMetadata extends Metadata {

  public static final Class<StringAttribute> ATTRIBUTE_CLASS = StringAttribute.class;
  private final StringProperty defaultValue;

  @JsonCreator
  public StringMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleStringProperty("");
  }

  @Override
  public Attribute makeAttribute() {
    return makeStringAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
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
    StringMetadata that = (StringMetadata) o;
    return Objects.equals(getDefaultValue(), that.getDefaultValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultValue);
  }

  @Override
  public String toString() {
    return String.format("String defaultValue=%s", this.defaultValue);
  }
}
