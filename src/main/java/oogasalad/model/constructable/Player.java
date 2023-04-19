package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;

public class Player extends GameConstruct {

  public static final String BASE_SCHEMA_NAME = "player";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String SCORE_ATTRIBUTE = "score";
  public static final String ICON_ATTRIBUTE = "icon";
  public static final String COLOR_ATTRIBUTE = "color";

  @Inject
  public Player(@JacksonInject SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
  }

  @JsonIgnore
  public StringAttribute getNameAttribute() {
    return StringAttribute.from(getAttribute(NAME_ATTRIBUTE));
  }

  @JsonIgnore
  public DoubleAttribute getScoreAttribute() {
    return DoubleAttribute.from(getAttribute(SCORE_ATTRIBUTE));
  }

  @JsonIgnore
  public String getName() {
    return getNameAttribute().getValue();
  }

  @JsonIgnore
  public Double getScore() {
    return getScoreAttribute().getValue();
  }

  // TODO: Uncomment once ImageAttribute and ColorAttribute are in place
//  @JsonIgnore
//  public ImageAttribute getImageAttribute() {
//    return ImageAttribute.from(getAttribute(ICON_ATTRIBUTE));
//  }
//
//  @JsonIgnore
//  public ColorAttribute getColorAttribute() {
//    return ColorAttribute.from(getAttribute(COLOR_ATTRIBUTE));
//  }


}
