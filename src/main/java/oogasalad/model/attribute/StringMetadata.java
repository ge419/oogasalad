package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringMetadata extends AbstractMetadata {

  public static final Class<StringAttribute> ATTRIBUTE_CLASS = StringAttribute.class;
  private final StringProperty defaultValue;

  @JsonCreator
  public StringMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleStringProperty("");
  }

  public static StringMetadata from(Metadata meta) {
    return getAs(meta, StringMetadata.class);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    String val = StringAttribute.from(attribute).getValue();
    return isValidValue(val);
  }

  public boolean isValidValue(String val) {
    // No preconditions
    return true;
  }

  @Override
  public Attribute makeAttribute() {
    return makeStringAttribute();
  }

  @Override
  @JsonIgnore
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public StringAttribute makeStringAttribute() {
    return new StringAttribute(getKey(), getDefaultValue());
  }

  public String getDefaultValue() {
    return defaultValue.get();
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue.set(defaultValue);
  }

  public StringProperty defaultValueProperty() {
    return defaultValue;
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
