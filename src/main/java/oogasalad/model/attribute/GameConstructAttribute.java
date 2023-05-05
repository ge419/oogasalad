package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@JsonTypeInfo(use = Id.CLASS)
public abstract class GameConstructAttribute extends AbstractAttribute {

  private final ObjectProperty<Optional<String>> id;

  protected GameConstructAttribute(String key, String id) {
    super(key);
    this.id = new SimpleObjectProperty<>(formId(id));
  }

  public static GameConstructAttribute from(Attribute attr) {
    return getAs(attr, GameConstructAttribute.class);
  }

  public ObjectProperty<Optional<String>> idProperty() {
    return id;
  }

  public Optional<String> getId() {
    return idProperty().get();
  }

  @JsonSetter("id")
  public void setId(String id) {
    idProperty().set(formId(id));
  }

  @JsonGetter("id")
  public String getUncheckedId() {
    return idProperty().get().orElse("");
  }

  private Optional<String> formId(String id) {
    if (id == null || id.isBlank()) {
      return Optional.empty();
    }
    return Optional.of(id);
  }

  @Override
  public String toString() {
    return String.format("Game Construct Attribute {value: %s}", this.id);
  }
}

