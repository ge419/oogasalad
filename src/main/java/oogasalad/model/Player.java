package oogasalad.model;

import java.util.HashMap;
import java.util.Map;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.AttributeMetadata;

public class Player implements Constructable {
  Map<String, AttributeMetadata<?>> schema;
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
    return getAttribute("id").toString();
  }

  public Map<String, BMetaData> getSchema() {
    return schema;
  }

  public void setSchema(Map<String, BMetaData> schema) {
    this.schema = schema;
  }

  public Map<String, BAttribute> getValues() {
    return values;
  }

  public void setValues(Map<String, BAttribute> values) {
    this.values = values;
  }
}
