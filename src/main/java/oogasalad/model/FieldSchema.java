package oogasalad.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class FieldSchema {
  private final String key;
  private final AttributeType type;

  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty description = new SimpleStringProperty();
  private final BooleanProperty editable = new SimpleBooleanProperty();
  private final BooleanProperty viewable = new SimpleBooleanProperty();
  private final ObjectProperty<?> defaultValue = new SimpleObjectProperty<>();

  public FieldSchema(String key, AttributeType type) {
    this.key = key;
    this.type = type;
  }

  public String getKey() {
    return key;
  }

  public AttributeType getType() {
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
    if (!clazz.isAssignableFrom(defaultValue.get().getClass())) {
      throw new IllegalArgumentException("invalid value cast");
    }

    return (ReadOnlyObjectProperty<T>) defaultValue;
  }
}
