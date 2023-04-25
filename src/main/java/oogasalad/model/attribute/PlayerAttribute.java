package oogasalad.model.attribute;

public class PlayerAttribute extends GameConstructAttribute {

  public PlayerAttribute(String key, String id) {
    super(key, id);
  }

  public static PlayerAttribute from(Attribute attr) {
    return AbstractAttribute.getAs(attr, PlayerAttribute.class);
  }
}
