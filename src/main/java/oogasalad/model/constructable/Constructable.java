package oogasalad.model.constructable;

import java.util.Map;
import oogasalad.model.attribute.Attribute;

public interface Constructable {
  Map<String, Attribute> getAttributes();
  void setAttributes(Map<String, Attribute> attributes);
  Attribute getAttribute(String key);
}