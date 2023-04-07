package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BAttribute {
  @JsonProperty("key")
  private final String key;
  @JsonProperty("value")
  private BValue value;

  public BAttribute() {
    this.key = "key";
    this.value = null;
  }

  public BAttribute(String key, BValue value) {
    this.key = key;
    this.value = value;
  }

  public String getValue() {
    return this.value.toString();
  }
  public String getKey(){return this.key;}

  public void updateValue(BValue val) {
    this.value = val;
  }

  @Override
  public String toString() {
    return this.key;
  }
}
