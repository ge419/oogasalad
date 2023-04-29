package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.ColorAttribute;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.ImageAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;

public class Player extends AbstractGameConstruct {

  public static final String BASE_SCHEMA_NAME = "player";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String SCORE_ATTRIBUTE = "score";
  public static final String ICON_ATTRIBUTE = "image";
  public static final String COLOR_ATTRIBUTE = "color";
  private final List<Piece> pieces;


  @Inject
  public Player(@JacksonInject SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
    pieces = new ArrayList<>();
  }

  @JsonIgnore
  public StringAttribute getNameAttribute() {
    return StringAttribute.from(getAttribute(NAME_ATTRIBUTE).get());
  }


  @JsonIgnore
  public DoubleAttribute getScoreAttribute() {
    return DoubleAttribute.from(getAttribute(SCORE_ATTRIBUTE).get());
  }

  @JsonIgnore
  public ImageAttribute getImageAttribute() {
    return ImageAttribute.from(getAttribute(ICON_ATTRIBUTE).get());
  }

  @JsonIgnore
  public ColorAttribute getColorAttribute() {
    return ColorAttribute.from(getAttribute(COLOR_ATTRIBUTE).get());
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
  public void setScore(double value) {
    getScoreAttribute().setValue(value);
  }

  @JsonIgnore
  public String getColor() {
    return getColorAttribute().getValue();
  }

  @JsonIgnore
  public void setColor(String color) {getColorAttribute().setValue(color);}

  @JsonIgnore
  public String getImage() {
    return getImageAttribute().getValue();
  }

  @JsonIgnore
  public void setImage(String imagePath) {getImageAttribute().setValue(imagePath);}

  @JsonIgnore
  public List<Piece> getPieces() {
    return pieces;
  }

}
