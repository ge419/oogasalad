package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BAttribute {
  @JsonProperty("key")
  private final String key;
  @JsonProperty("value")
  private String value;
  @JsonProperty("type")
  private String type;
  @JsonProperty("defaultValue")
  private String defaultValue;
  @JsonProperty("isEditable")
  private boolean isEditable;

  public BAttribute() {
    this.key = "key";
    this.isEditable = true;
    this.defaultValue = "defaultValue";
    this.type = "type";
    this.value = "value";
  }

  public BAttribute(String key, boolean isEditable, String defaultValue, String type, String value) {
    this.key = key;
    this.isEditable = isEditable;
    this.defaultValue = defaultValue;
    this.type = type;
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public void updateValue(String val) {
    if (!isEditable) throw new UnsupportedOperationException();
    this.value = val;
  }

  public boolean getEditStatus() {
    return this.isEditable;
  }

  @Override
  public String toString() {
    return this.key;
  }
}
