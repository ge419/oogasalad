package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BooleanMetadata extends Metadata {

  public static final Class<BooleanAttribute> ATTRIBUTE_CLASS = BooleanAttribute.class;
  private final BooleanProperty defaultValue;

  @JsonCreator
  public BooleanMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleBooleanProperty(false);
  }

  @Override
  public Attribute makeAttribute() {
    return null;
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public BooleanAttribute makeBooleanAttribute() {
    return new BooleanAttribute(getKey(), getDefaultValue());
  }

  public boolean getDefaultValue() {
    return defaultValue.get();
  }

  public BooleanProperty defaultValueProperty() {
    return defaultValue;
  }

  public void setDefaultValue(boolean defaultValue) {
    this.defaultValue.set(defaultValue);
  }
}
