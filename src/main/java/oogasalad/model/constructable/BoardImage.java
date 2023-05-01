package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.inject.Inject;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.ImageAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.view.Coordinate;

public class BoardImage extends AbstractGameConstruct {

  public static final String SCHEMA_NAME = "boardImage";
  public static final String IMAGE_ATTRIBUTE = "image";
  public static final String POSITION_ATTRIBUTE = "position";
  public static final String SCALE_ATTRIBUTE = "scale";

  @Inject
  public BoardImage(SchemaDatabase database) {
    super(SCHEMA_NAME, database);
  }

  @JsonIgnore
  public ImageAttribute imageAttribute() {
    return ImageAttribute.from(getAttribute(IMAGE_ATTRIBUTE).get());
  }

  @JsonIgnore
  public PositionAttribute positionAttribute() {
    return PositionAttribute.from(getAttribute(POSITION_ATTRIBUTE).get());
  }

  @JsonIgnore
  public DoubleAttribute scaleAttribute() {
    return DoubleAttribute.from(getAttribute(SCALE_ATTRIBUTE).get());
  }

  @JsonIgnore
  public String getImage() {
    return imageAttribute().getValue();
  }

  @JsonIgnore
  public void setImage(String name) {
    imageAttribute().setValue(name);
  }

  @JsonIgnore
  public void setCoordinate(Coordinate coordinate) {
    positionAttribute().setCoordinate(coordinate);
  }
}
