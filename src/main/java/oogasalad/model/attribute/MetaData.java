package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaData {
  @JsonProperty("key")
  private final String key;
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
  private final String defaultAttr;

  public MetaData() {
    key = null;
    type = null;
    name = null;
    description = null;
    editable = null;
    viewable = null;
    defaultAttr = null;
  }

  public MetaData(String key, String type, String name, String description, boolean editable, boolean viewable, String defaultVal) {
    this.key = key;
    this.type = type;
    this.defaultAttr = defaultVal;
    this.name = name;
    this.description = description;
    this.editable = editable;
    this.viewable = viewable;
  }

  public String getKey() {
    return key;
  }

//  public T getDefaultAttr() {
//    return defaultAttr;
//  }

//  public String getName() {
//    return name.get();
//  }
//
//  public StringProperty nameProperty() {
//    return name;
//  }
//
//  public String getDescription() {
//    return description.get();
//  }
//
//  public StringProperty descriptionProperty() {
//    return description;
//  }
//
//  public boolean isEditable() {
//    return editable.get();
//  }
//
//  public BooleanProperty editableProperty() {
//    return editable;
//  }
//
//  public boolean isViewable() {
//    return viewable.get();
//  }
//
//  public BooleanProperty viewableProperty() {
//    return viewable;
//  }
}
