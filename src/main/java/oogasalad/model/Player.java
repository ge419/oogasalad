package oogasalad.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Purpose of BAttribute is that we don't copy information about if an attribute is editable, its name, etc. around in every class.
 * That way we have a single source of truth, and changing metadata on an attribute scheme will change it everywhere.
 *
 * I am still wondering about whether we would need a separate 'schema' variable, as all information incl. type, key, value, editStatus
 * is stored inside BAttribute instance. This would become more clear as we work with the Builder and Jackson deserializer.
 */
public class Player implements Constructable {
  Map<String, BMetaData> schema;
  Map<String, BAttribute> values;
  public Player() {
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
  public boolean getEditableStatus(String key) {
    return this.values.get(key).getEditStatus();
  }

  @Override
  public String toString() {
    return this.values.get("id").getValue();
  }
}
