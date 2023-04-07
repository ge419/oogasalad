package oogasalad.model;

import java.util.Map;

public interface Constructable {
  void setAttributes(Map<String, BAttribute> attributes);
  String getAttributeValue(String key);
}
