package oogasalad.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Constructable {

  private Map<String, BAttribute> values;

  public Constructable() {
    values = new HashMap<>();
  }

  public Map<String, BAttribute> getValues() {
    return this.values;
  }

  public void setValues(Map<String, BAttribute> attributes) {
    this.values = attributes;
  }

  public String getAttributeValue(String key) {
    return this.values.get(key).getValue();
  }

  public void updateAttributeValue(String key, BValue value) {
    this.values.get(key).updateValue(value);
  }
  @Override
  public String toString() {
    return this.values.get("id").getValue();
  }
}