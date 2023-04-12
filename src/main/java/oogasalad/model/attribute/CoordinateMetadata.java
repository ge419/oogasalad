package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CoordinateMetadata extends Metadata {

  public static final Class<CoordinateAttribute> ATTRIBUTE_CLASS = CoordinateAttribute.class;
  private final DoubleProperty defaultX;
  private final DoubleProperty defaultY;

  @JsonCreator
  public CoordinateMetadata(@JsonProperty("key") String key) {
    super(key);
    defaultX = new SimpleDoubleProperty(0);
    defaultY = new SimpleDoubleProperty(0);
  }

  @Override
  public Attribute makeAttribute() {
    return makeCoordinateAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }


  public CoordinateAttribute makeCoordinateAttribute() {
    return new CoordinateAttribute(getKey(), getDefaultX(), getDefaultY());
  }

  public double getDefaultX() {
    return defaultX.get();
  }

  public DoubleProperty defaultXProperty() {
    return defaultX;
  }

  public void setDefaultX(double defaultX) {
    this.defaultX.set(defaultX);
  }

  public double getDefaultY() {
    return defaultY.get();
  }

  public DoubleProperty defaultYProperty() {
    return defaultY;
  }

  public void setDefaultY(double defaultY) {
    this.defaultY.set(defaultY);
  }

  @Override
  public String toString() {
    return "CoordinateAttributeMetadata{" +
        "defaultX=" + defaultX +
        ", defaultY=" + defaultY +
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
    CoordinateMetadata that = (CoordinateMetadata) o;
    return Objects.equals(getDefaultX(), that.getDefaultX()) && Objects.equals(getDefaultY(),
        that.getDefaultY());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultX, defaultY);
  }
}
