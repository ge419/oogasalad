package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.TileListAttribute;
import oogasalad.view.Coordinate;

public class Tile extends GameConstruct {

  public static final String BASE_SCHEMA_NAME = "basicTile";
  public static final String TYPE_ATTRIBUTE = "type";
  public static final String NEXT_ATTRIBUTE = "next";
  public static final String POSITION_ATTRIBUTE = "position";
  public static final String WIDTH_ATTRIBUTE = "width";
  public static final String HEIGHT_ATTRIBUTE = "height";
  public static final String INFO_ATTRIBUTE = "info";
  public static final String IMAGE_ATTRIBUTE = "image";
  public static final String COLOR_ATTRIBUTE = "color";


  @Inject
  public Tile(@JacksonInject SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
  }

  @JsonIgnore
  public TileListAttribute getNextAttribute() {
    return TileListAttribute.from(getAttribute(NEXT_ATTRIBUTE));
  }

  @JsonIgnore
  public List<String> getNextTileIds() {
    return getNextAttribute().getTileIds();
  }

  @JsonIgnore
  public PositionAttribute getPositionAttribute() {
    return PositionAttribute.from(getAttribute(POSITION_ATTRIBUTE));
  }

  @JsonIgnore
  public Coordinate getCoordinate() {
    return getPositionAttribute().getCoordinate();
  }

  @JsonIgnore
  public void setCoordinate(Coordinate coordinate) {
    getPositionAttribute().setCoordinate(coordinate);
  }

  @JsonIgnore
  public double getX() {
    return getCoordinate().getXCoor();
  }

  @JsonIgnore
  public void setX(double x) {
    getPositionAttribute().setX(x);
  }

  @JsonIgnore
  public double getY() {
    return getCoordinate().getYCoor();
  }

  @JsonIgnore
  public void setY(double y) {
    getPositionAttribute().setY(y);
  }

  @JsonIgnore
  public double getWidth() {
    return DoubleAttribute.from(getAttribute(WIDTH_ATTRIBUTE)).getValue();
  }

  @JsonIgnore
  public double getHeight() {
    return DoubleAttribute.from(getAttribute(HEIGHT_ATTRIBUTE)).getValue();
  }

  @JsonIgnore
  public String getInfo() {
    return StringAttribute.from(getAttribute(INFO_ATTRIBUTE)).getValue();
  }

  @JsonIgnore
  public String getImage() {
    return StringAttribute.from(getAttribute(IMAGE_ATTRIBUTE)).getValue();
  }

  @JsonIgnore
  public String getType() {
    return StringAttribute.from(getAttribute(TYPE_ATTRIBUTE)).getValue();
  }

  @JsonIgnore
  public String getColor() {
    return StringAttribute.from(getAttribute(COLOR_ATTRIBUTE)).getValue();
  }

}

