package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import oogasalad.view.Coordinate;

public class PositionMetadata extends AbstractMetadata {

  public static final Class<PositionAttribute> ATTRIBUTE_CLASS = PositionAttribute.class;
  private final DoubleProperty defaultX;
  private final DoubleProperty defaultY;
  private final DoubleProperty defaultAngle;

  @JsonCreator
  public PositionMetadata(@JsonProperty("key") String key) {
    super(key);
    defaultX = new SimpleDoubleProperty(0);
    defaultY = new SimpleDoubleProperty(0);
    defaultAngle = new SimpleDoubleProperty(0);
  }

  public static PositionMetadata from(Metadata meta) {
    return getAs(meta, PositionMetadata.class);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    Coordinate coord = PositionAttribute.from(attribute).getCoordinate();
    return isValidCoordinate(coord);
  }

  @Override
  public Attribute makeAttribute() {
    return makeCoordinateAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public boolean isValidCoordinate(Coordinate coordinate) {
    // No preconditions on position
    return true;
  }

  public PositionAttribute makeCoordinateAttribute() {
    return new PositionAttribute(getKey(), getDefaultX(), getDefaultY(), getDefaultAngle());
  }

  public double getDefaultX() {
    return defaultX.get();
  }

  public void setDefaultX(double defaultX) {
    this.defaultX.set(defaultX);
  }

  public DoubleProperty defaultXProperty() {
    return defaultX;
  }

  public double getDefaultY() {
    return defaultY.get();
  }

  public void setDefaultY(double defaultY) {
    this.defaultY.set(defaultY);
  }

  public DoubleProperty defaultYProperty() {
    return defaultY;
  }

  public double getDefaultAngle() {
    return defaultAngle.get();
  }

  public void setDefaultAngle(double defaultAngle) {
    this.defaultAngle.set(defaultAngle);
  }

  public DoubleProperty defaultYAngle() {
    return defaultAngle;
  }

  @Override
  public String toString() {
    return "CoordinateAttributeMetadata{" +
        "defaultX=" + defaultX +
        ", defaultY=" + defaultY +
        ", defaultAngle=" + defaultAngle +
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
    PositionMetadata that = (PositionMetadata) o;
    return Objects.equals(getDefaultX(), that.getDefaultX()) && Objects.equals(getDefaultY(),
        that.getDefaultY()) && Objects.equals(getDefaultAngle(), that.defaultAngle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), defaultX, defaultY, defaultAngle);
  }
}
