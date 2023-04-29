package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import oogasalad.view.Coordinate;

/**
 * Holds a 2D coordinate.
 *
 * @author Dominic Martinez
 */
public class PositionAttribute extends AbstractAttribute {

  private final DoubleProperty x;
  private final DoubleProperty y;
  private final DoubleProperty angle;

  @JsonCreator
  public PositionAttribute(
      @JsonProperty("key") String key,
      @JsonProperty("x") double x,
      @JsonProperty("y") double y,
      @JsonProperty("angle") double angle

  ) {
    super(key);
    this.x = new SimpleDoubleProperty(x);
    this.y = new SimpleDoubleProperty(y);
    this.angle = new SimpleDoubleProperty(angle);
  }

  public static PositionAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, PositionAttribute.class);
  }

  public double getX() {
    return x.get();
  }

  public void setX(double x) {
    this.x.set(x);
  }

  public DoubleProperty xProperty() {
    return x;
  }

  public double getY() {
    return y.get();
  }

  public void setY(double y) {
    this.y.set(y);
  }

  public DoubleProperty yProperty() {
    return y;
  }

  public double getAngle() { return angle.get(); };

  public void setAngle(double angle) { this.angle.set(angle);}

  public DoubleProperty angleProperty() {
    return angle;
  }

  @JsonIgnore
  public Coordinate getCoordinate() {
    return new Coordinate(getX(), getY(), getAngle());
  }

  @JsonIgnore
  public void setCoordinate(Coordinate coordinate) {
    setX(coordinate.getXCoor());
    setY(coordinate.getYCoor());
    setAngle(coordinate.getAngle());
  }

  @Override
  public String toString() {
    return "PositionAttribute{" +
        "x=" + getX() +
        ", y=" + getY() +
        ", angle=" + getAngle() +
        '}';
  }
}
