package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.ColorAttribute;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.ImageAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.TileAttribute;

public class Player extends AbstractGameConstruct {

  public static final String BASE_SCHEMA_NAME = "basicPlayer";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String SCORE_ATTRIBUTE = "score";
  public static final String ICON_ATTRIBUTE = "image";
  public static final String COLOR_ATTRIBUTE = "color";
  public static final String POSITION_ATTRIBUTE = "position";

  @Inject
  public Player(@JacksonInject SchemaDatabase database) {
    super(List.of(BASE_SCHEMA_NAME), database);
  }

  @JsonIgnore
  public StringAttribute getNameAttribute() {
    return StringAttribute.from(getAttribute(NAME_ATTRIBUTE));
  }
  @JsonIgnore
  public TileAttribute tileAttribute() {
    return TileAttribute.from(getAttribute(POSITION_ATTRIBUTE));
  }

  @JsonIgnore
  public String getCurrentTile() {
    return tileAttribute().getId();
  }

  @JsonIgnore
  public void moveToTile(String id) {
    tileAttribute().setId(id);
  }


  @JsonIgnore
  public DoubleAttribute getScoreAttribute() {
    return DoubleAttribute.from(getAttribute(SCORE_ATTRIBUTE));
  }

  @JsonIgnore
  public ImageAttribute getImageAttribute() {
    return ImageAttribute.from(getAttribute(ICON_ATTRIBUTE));
  }

  @JsonIgnore
  public ColorAttribute getColorAttribute() {
    return ColorAttribute.from(getAttribute(COLOR_ATTRIBUTE));
  }

  @JsonIgnore
  public String getName() {
    return getNameAttribute().getValue();
  }
  @JsonIgnore
  public void setName(String newName) {
    getNameAttribute().setValue(newName);
  }

  @JsonIgnore
  public Double getScore() {
    return getScoreAttribute().getValue();
  }

  @JsonIgnore
  public String getColor() {
    return getColorAttribute().getValue();
  }

  @JsonIgnore
  public String getImage() {
    return getImageAttribute().getValue();
  }

}
