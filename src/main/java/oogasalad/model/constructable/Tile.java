package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.inject.Inject;
import oogasalad.view.Coordinate;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileListAttribute;

public class Tile extends GameConstruct {

  public static final String BASE_SCHEMA_NAME = "basicTile";
  public static final String NEXT_ATTRIBUTE = "next";
  public static final String POSITION_ATTRIBUTE = "position";
  public static final String WIDTH_ATTRIBUTE = "width";
  public static final String HEIGHT_ATTRIBUTE = "height";

  @Inject
  public Tile(@JacksonInject SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
  }

  @JsonIgnore
  public List<String> getNextTileIds() {
    return TileListAttribute.from(getAttribute(NEXT_ATTRIBUTE)).getTileIds();
  }

  @JsonIgnore
  public Coordinate getCoordinate() {
    return PositionAttribute.from(getAttribute(POSITION_ATTRIBUTE)).getCoordinate();
  }

  @JsonIgnore
  public double getX() {
    return getCoordinate().getXCoor();
  }

  @JsonIgnore
  public double getY() {
    return getCoordinate().getYCoor();
  }

  @JsonIgnore
  public double getWidth() {
    return DoubleAttribute.from(getAttribute(WIDTH_ATTRIBUTE)).getValue();
  }

  @JsonIgnore
  public double getHeight() {
    return DoubleAttribute.from(getAttribute(HEIGHT_ATTRIBUTE)).getValue();
  }
}

