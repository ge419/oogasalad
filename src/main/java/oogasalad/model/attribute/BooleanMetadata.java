package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BooleanMetadata extends AbstractMetadata {

  public static final Class<BooleanAttribute> ATTRIBUTE_CLASS = BooleanAttribute.class;
  private final BooleanProperty defaultValue;

  @JsonCreator
  public BooleanMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleBooleanProperty(false);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    // No preconditions on booleans
    return true;
  }

  @Override
  public Attribute makeAttribute() {
    return makeBooleanAttribute();
  }

  @Override
  @JsonIgnore
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public static BooleanMetadata from(Metadata meta) {
    return getAs(meta, BooleanMetadata.class);
  }

  public BooleanAttribute makeBooleanAttribute() {
    return new BooleanAttribute(getKey(), getDefaultValue());
  }

  public boolean getDefaultValue() {
    return defaultValue.get();
  }

  public void setDefaultValue(boolean defaultValue) {
    this.defaultValue.set(defaultValue);
  }

  public BooleanProperty defaultValueProperty() {
    return defaultValue;
  }
}
