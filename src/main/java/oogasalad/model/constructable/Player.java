package oogasalad.model.constructable;

import java.util.HashMap;
import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.MetaData;

public class Player implements Constructable {
  Map<String, MetaData> schema;
  Map<String, Attribute> values;
  public Player() {
    schema = new HashMap<>();
    values = new HashMap<>();
  }

  @Override
  public void setAttributes(Map<String, Attribute> attributes) {
    this.values = attributes;
  }

  @Override
  public Map<String, Attribute> getAttributes() {
    return values;
  }

  @Override
  public Attribute getAttribute(String key) {
    return this.values.get(key);
  }

  @Override
  public String toString() {
    return getAttribute("name").getKey();
  }
}
