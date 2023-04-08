package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BAttribute {
  @JsonProperty("key")
  private final String key;
  @JsonProperty("value")
  private String value;

  public BAttribute() {
    this.key = "key";
    this.value = null;
  }

  public BAttribute(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getValue() {
    return this.value.toString();
  }

  public void updateValue(String val) {
    this.value = val;
  }

  @Override
  public String toString() {
    return this.key;
  }
}
