package oogasalad.model;

import java.util.HashMap;
import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.AttributeMetadata;

public class Tile implements Constructable {
  Map<String, AttributeMetadata> schema;
  Map<String, Attribute> values;

  public Tile() {
    schema = new HashMap<>();
    values = new HashMap<>();
  }

  @Override
  public Map<String, Attribute> getAttributes() {
    return values;
  }

  @Override
  public void setAttributes(Map<String, Attribute> attributes) {
    this.values = attributes;
  }
  @Override
  public Attribute getAttribute(String key) {
    return this.values.get(key);
  }

  @Override
  public String toString() {
    return getAttribute("id").toString();
  }
}

