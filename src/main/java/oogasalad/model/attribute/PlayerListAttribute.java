package oogasalad.model.attribute;

import java.util.List;

public class PlayerListAttribute extends GameConstructListAttribute {

  public PlayerListAttribute(String key, List<String> playerIds) {
    super(key, playerIds);
  }

  public static PlayerListAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, PlayerListAttribute.class);
  }
}
