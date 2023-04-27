package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageMetadata extends StringMetadata{

  public static final Class<ImageAttribute> ATTRIBUTE_CLASS = ImageAttribute.class;
  private final StringProperty defaultValue;

  @JsonCreator
  public ImageMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleStringProperty("view.gameplay/default.jpg");
  }
  public ImageAttribute makeImageAttribute() {
    return new ImageAttribute(getKey(), getDefaultValue());
  }
}
