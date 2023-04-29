package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ColorMetadata extends StringMetadata {

  public static final Class<ColorAttribute> ATTRIBUTE_CLASS = ColorAttribute.class;
  private final StringProperty defaultValue;

  @JsonCreator
  public ColorMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleStringProperty("FFFFFF");
  }

  @Override
  public Attribute makeAttribute() {
    return makeColorAttribute();
  }

  public ColorAttribute makeColorAttribute() {
    return new ColorAttribute(getKey(), getDefaultValue());
  }
}
