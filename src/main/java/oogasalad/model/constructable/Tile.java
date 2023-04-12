package oogasalad.model.constructable;

import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;

public class Tile extends AttributeObject {

  @Inject
  public Tile(String tileType, SchemaDatabase database) {
    super(tileType, database);
  }

  //TODO: Replace with Tile ID
  @Override
  public String toString() {
    return String.format("This is Tile ID %d", IntAttribute.from(getAttribute("width")).getValue());
  }


}

