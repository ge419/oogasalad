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
    // TODO: Fix check, provide default if bad??
//    String[] splitVal = val.split("\\.");
//    if (validExtensions.contains(splitVal[1])) {
//      return true;
//    }
    return true;
  }
  @Override
  public Attribute makeAttribute() {
    return makeImageAttribute();
  }

  public ImageAttribute makeImageAttribute() {
    return new ImageAttribute(getKey(), "");
  }
}
