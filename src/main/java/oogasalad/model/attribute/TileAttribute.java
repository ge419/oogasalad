package oogasalad.model.attribute;

import java.util.Optional;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import oogasalad.model.Tile;

public class TileAttribute extends Attribute {
  private final IntegerProperty value;

  public TileAttribute(String key, int value) {
    super(key);
    this.value = new SimpleIntegerProperty(value);
  }

  public static TileAttribute from(Attribute attr) {
    return Attribute.getAttributeAs(attr, TileAttribute.class);
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
    return "TileAttribute{" +
        "value=" + value +
        '}';
  }
}
