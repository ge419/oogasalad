package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageMetadata extends StringMetadata {

  public static final Class<ImageAttribute> ATTRIBUTE_CLASS = ImageAttribute.class;
  private final StringProperty defaultValue;
  private final List<String> validExtensions = List.of("jpg", "png");

  @JsonCreator
  public ImageMetadata(@JsonProperty("key") String key) {
    super(key);
    this.defaultValue = new SimpleStringProperty("view.gameplay/default.jpg");
  }

  @Override
  public boolean isValidValue(String val) {
    if (val == null) {
      return false;
    }
    String[] splitVal = val.split("\\.");
    return validExtensions.contains(splitVal[splitVal.length - 1]);
  }

  @Override
  public Attribute makeAttribute() {
    return makeImageAttribute();
  }

  public ImageAttribute makeImageAttribute() {
    return new ImageAttribute(getKey(), "");
  }
}
