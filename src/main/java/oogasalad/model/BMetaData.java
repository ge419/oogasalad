package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BMetaData {
  private String key;
  private final BType type;
  private final StringProperty name;
  private final StringProperty description;
  private final BooleanProperty editable;
  private final BooleanProperty viewable;
  private final ObjectProperty<?> defaultVal;

  public BMetaData(String key, BType type) {
    this.key = key;
    this.type = type;
    name = new SimpleStringProperty();
    description = new SimpleStringProperty();
    editable = new SimpleBooleanProperty();
    viewable = new SimpleBooleanProperty();
    defaultVal = new SimpleObjectProperty<>();
  }

  public String getKey() {
    return key;
  }

  public BType getType() {
    return type;
  }

  public String getName() {
    return name.get();
  }

  public ReadOnlyStringProperty nameProperty() {
    return name;
  }

  public String getDescription() {
    return description.get();
  }

  public ReadOnlyStringProperty descriptionProperty() {
    return description;
  }

  public boolean isEditable() {
    return editable.get();
  }

  public ReadOnlyBooleanProperty editableProperty() {
    return editable;
  }

  public boolean isViewable() {
    return viewable.get();
  }

  public ReadOnlyBooleanProperty viewableProperty() {
    return viewable;
  }

  public <T> T getDefaultValue(Class<? extends T> clazz) {
    return defaultValueProperty(clazz).getValue();
  }

  public <T> ReadOnlyObjectProperty<T> defaultValueProperty(Class<? extends T> clazz) {
    if (!clazz.isAssignableFrom(defaultVal.get().getClass())) {
      throw new IllegalArgumentException("invalid value cast");
    }

    return (ReadOnlyObjectProperty<T>) defaultVal;
  }

}
