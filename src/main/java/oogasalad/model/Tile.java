package oogasalad.model;

import java.util.HashMap;
import java.util.Map;

public class Tile implements Constructable {
  Map<String, BMetaData> schema;
  Map<String, BAttribute> values;

  public Tile() {
    schema = new HashMap<>();
    values = new HashMap<>();
  }
  @Override
  public void setAttributes(Map<String, BAttribute> attributes) {
    this.values = attributes;
  }
  @Override
  public String getAttributeValue(String key) {
    return this.values.get(key).getValue();
  }
  @Override
  public void updateAttributeValue(String key, BValue value) {
    this.values.get(key).updateValue(value);
  }
  @Override
  public String toString() {
    return this.values.get("id").getValue();
  }
}

