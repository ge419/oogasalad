package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DoubleMetadata extends AbstractMetadata {

  public static final Class<DoubleAttribute> ATTRIBUTE_CLASS = DoubleAttribute.class;
  private final DoubleProperty defaultValue;
  private final DoubleProperty minValue;
  private final DoubleProperty maxValue;

  @JsonCreator
  public DoubleMetadata(@JsonProperty("key") String key) {
    super(key);
    defaultValue = new SimpleDoubleProperty(0);
    minValue = new SimpleDoubleProperty(Double.MIN_VALUE);
    maxValue = new SimpleDoubleProperty(Double.MAX_VALUE);
  }

  @Override
  public Attribute makeAttribute() {
    return makeDoubleAttribute();
  }

  @Override
  @JsonIgnore
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public static DoubleMetadata from(Metadata meta) {
    return getAs(meta, DoubleMetadata.class);
  }

  public DoubleAttribute makeDoubleAttribute() {
    return new DoubleAttribute(getKey(), getDefaultValue());
  }

  public double getDefaultValue() {
    return defaultValue.get();
  }

  public void setDefaultValue(double defaultValue) {
    this.defaultValue.set(defaultValue);
  }

  public DoubleProperty defaultValueProperty() {
    return defaultValue;
  }

  public double getMinValue() {
    return minValue.get();
  }

  public void setMinValue(double minValue) {
    this.minValue.set(minValue);
  }

  public DoubleProperty minValueProperty() {
    return minValue;
  }

  public double getMaxValue() {
    return maxValue.get();
  }

  public void setMaxValue(double maxValue) {
    this.maxValue.set(maxValue);
  }

  public DoubleProperty maxValueProperty() {
    return maxValue;
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
    DoubleMetadata that = (DoubleMetadata) o;
    return Objects.equals(defaultValue, that.defaultValue) && Objects.equals(
        minValue, that.minValue) && Objects.equals(maxValue, that.maxValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultValue, minValue, maxValue);
  }
}
