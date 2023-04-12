package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonTypeInfo(use= Id.CLASS)
public abstract class AttributeMetadata {
  private final String key;
  private final StringProperty name;
  private final StringProperty description;
  private final BooleanProperty editable;
  private final BooleanProperty viewable;

  protected AttributeMetadata(String key) {
    this.key = key;
    name = new SimpleStringProperty("");
    description = new SimpleStringProperty("");
    editable = new SimpleBooleanProperty(true);
    viewable = new SimpleBooleanProperty(true);
  }

  public abstract Attribute makeAttribute();

  public String getKey() {
    return key;
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getDescription() {
    return description.get();
  }

  public StringProperty descriptionProperty() {
    return description;
  }

  public void setDescription(String description) {
    this.description.set(description);
  }

  public boolean isEditable() {
    return editable.get();
  }

  public BooleanProperty editableProperty() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable.set(editable);
  }

  public boolean isViewable() {
    return viewable.get();
  }

  public BooleanProperty viewableProperty() {
    return viewable;
  }

  public void setViewable(boolean viewable) {
    this.viewable.set(viewable);
  }

  @Override
  public String toString() {
    return "AttributeMetadata{" +
        "key='" + key + '\'' +
        ", name=" + name +
        ", description=" + description +
        ", editable=" + editable +
        ", viewable=" + viewable +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttributeMetadata metadata = (AttributeMetadata) o;
    return Objects.equals(key, metadata.key) && Objects.equals(getName(),
        metadata.getName()) && Objects.equals(getDescription(), metadata.getDescription())
        && Objects.equals(isEditable(), metadata.isEditable()) && Objects.equals(isViewable(),
        metadata.isViewable());
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name, description, editable, viewable);
  }
}
