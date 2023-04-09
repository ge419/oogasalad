package oogasalad.model.attribute;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AttributeMetadata<T extends Attribute> {
  private final String key;
  private final StringProperty name;
  private final StringProperty description;
  private final BooleanProperty editable;
  private final BooleanProperty viewable;
  private final T defaultAttr;

  public AttributeMetadata(String key, T defaultAttr) {
    this.key = key;
    this.defaultAttr = defaultAttr;
    name = new SimpleStringProperty();
    description = new SimpleStringProperty();
    editable = new SimpleBooleanProperty();
    viewable = new SimpleBooleanProperty();
  }

  public String getKey() {
    return key;
  }

  public T getDefaultAttr() {
    return defaultAttr;
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public String getDescription() {
    return description.get();
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public boolean isEditable() {
    return editable.get();
  }

  public BooleanProperty editableProperty() {
    return editable;
  }

  public boolean isViewable() {
    return viewable.get();
  }

  public BooleanProperty viewableProperty() {
    return viewable;
  }
}
