package oogasalad.model.attribute;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntAttribute extends Attribute {
  private final IntegerProperty value;

  public IntAttribute(String key, int value) {
    super(key);
    this.value = new SimpleIntegerProperty(value);
  }

  public static IntAttribute from(Attribute attr) {
    return Attribute.getAttributeAs(attr, IntAttribute.class);
  }

  public int getValue() {
    return value.get();
  }

  public IntegerProperty valueProperty() {
    return value;
  }

  public void setValue(int value) {
    this.value.set(value);
  }

  @Override
  public String toString() {
    return "IntAttribute{" +
        "value=" + value +
        '}';
  }
}
