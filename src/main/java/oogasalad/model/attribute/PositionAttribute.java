package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import oogasalad.view.Coordinate;

/**
 * Holds a 2D coordinate.
 *
 * @author Dominic Martinez
 */
public class PositionAttribute extends Attribute {
  private final DoubleProperty x;
  private final DoubleProperty y;

  @JsonCreator
  public PositionAttribute(
      @JsonProperty("key") String key,
      @JsonProperty("x") double x,
      @JsonProperty("y") double y
  ) {
    super(key);
    this.x = new SimpleDoubleProperty(x);
    this.y = new SimpleDoubleProperty(y);
  }

  public static PositionAttribute from(Attribute attr) {
    return Attribute.getAs(attr, PositionAttribute.class);
  }

  public double getX() {
    return x.get();
  }

  public DoubleProperty xProperty() {
    return x;
  }

  public void setX(double x) {
    this.x.set(x);
  }

  public double getY() {
    return y.get();
  }

  public DoubleProperty yProperty() {
    return y;
  }

  public void setY(double y) {
    this.y.set(y);
  }

  public Coordinate getCoordinate() {
    return new Coordinate(getX(), getY());
  }

  public void setCoordinate(Coordinate coordinate) {
    setX(coordinate.getXCoor());
    setY(coordinate.getYCoor());
  }

  @Override
  public String toString() {
    return "PositionAttribute{" +
        "x=" + getX() +
        ", y=" + getY() +
        '}';
  }
}
