package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntMetadata extends Metadata {

  public static final Class<IntAttribute> ATTRIBUTE_CLASS = IntAttribute.class;
  private final IntegerProperty defaultValue;
  private final IntegerProperty minValue;
  private final IntegerProperty maxValue;

  @JsonCreator
  public IntMetadata(@JsonProperty("key") String key) {
    super(key);
    defaultValue = new SimpleIntegerProperty(0);
    minValue = new SimpleIntegerProperty(Integer.MIN_VALUE);
    maxValue = new SimpleIntegerProperty(Integer.MAX_VALUE);
  }

  @Override
  public Attribute makeAttribute() {
    return makeIntAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public IntAttribute makeIntAttribute() {
    return new IntAttribute(getKey(), getDefaultValue());
  }

  public int getDefaultValue() {
    return defaultValue.get();
  }

  public IntegerProperty defaultValueProperty() {
    return defaultValue;
  }

  public void setDefaultValue(int defaultValue) {
    this.defaultValue.set(defaultValue);
  }

  public int getMinValue() {
    return minValue.get();
  }

  public IntegerProperty minValueProperty() {
    return minValue;
  }

  public void setMinValue(int minValue) {
    this.minValue.set(minValue);
  }

  public int getMaxValue() {
    return maxValue.get();
  }

  public IntegerProperty maxValueProperty() {
    return maxValue;
  }

  public void setMaxValue(int maxValue) {
    this.maxValue.set(maxValue);
  }

  @Override
  public String toString() {
    return "IntAttributeMetadata{" +
        "defaultValue=" + defaultValue +
        ", minValue=" + minValue +
        ", maxValue=" + maxValue +
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
    IntMetadata that = (IntMetadata) o;
    return Objects.equals(getDefaultValue(), that.getDefaultValue()) && Objects.equals(
        getMinValue(), that.getMinValue()) && Objects.equals(getMaxValue(), that.getMaxValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultValue, minValue, maxValue);
  }
}
