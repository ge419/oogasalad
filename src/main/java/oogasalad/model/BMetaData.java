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
  @JsonProperty("key")
  private String key;
  @JsonProperty("type")
  private final String type;
  @JsonProperty("name")
  private final String name;
  @JsonProperty("description")
  private final String description;
  @JsonProperty("editable")
  private final Boolean editable;
  @JsonProperty("viewable")
  private final Boolean viewable;
  @JsonProperty("defaultVal")
  private final Object defaultVal;

  public  BMetaData() {
    this.key = "key";
    this.type = "type";
    name = "name";
    description = "description";
    editable = false;
    viewable = true;
    defaultVal = null;
  }

//  public BMetaData(String key, String type) {
//    this.key = key;
//    this.type = type;
//    name = new SimpleStringProperty();
//    description = new SimpleStringProperty();
//    editable = new SimpleBooleanProperty();
//    viewable = new SimpleBooleanProperty();
//    defaultVal = new SimpleObjectProperty<>();
//  }

  public BMetaData(String key, String type, String name, String description,
      boolean editable, boolean viewable, String defaultVal) {
    this.key = key;
    this.type = type;
    this.name = name;
    this.description = description;
    this.editable = editable;
    this.viewable = viewable;
    this.defaultVal = defaultVal;
  }

  public String getKey() {
    return key;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

//  public ReadOnlyStringProperty nameProperty() {
//    return name;
//  }

//  public String getDescription() {
//    return description.get();
//  }

//  public ReadOnlyStringProperty descriptionProperty() {
//    return description;
//  }

//  public boolean isEditable() {
//    return editable.get();
//  }

//  public ReadOnlyBooleanProperty editableProperty() {
//    return editable;
//  }

//  public boolean isViewable() {
//    return viewable.get();
//  }

//  public ReadOnlyBooleanProperty viewableProperty() {
//    return viewable;
//  }

//  public <T> T getDefaultValue(Class<? extends T> clazz) {
//    return defaultValueProperty(clazz).getValue();
//  }

//  public <T> ReadOnlyObjectProperty<T> defaultValueProperty(Class<? extends T> clazz) {
//    if (!clazz.isAssignableFrom(defaultVal.get().getClass())) {
//      throw new IllegalArgumentException("invalid value cast");
//    }
//
//    return (ReadOnlyObjectProperty<T>) defaultVal;
//  }

}
