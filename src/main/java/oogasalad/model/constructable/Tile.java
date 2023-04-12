package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.SchemaDatabase;

public class Tile extends GameConstruct {

  @JsonCreator
  public Tile(@JsonProperty("schema") String tileType) {
    super(tileType);
  }
  public Tile(String tileType, SchemaDatabase database) {
    super(tileType, database);
  }

  //TODO: Replace with Tile ID
  @Override
  public String toString() {
    return String.format("This is Tile X %.2f", PositionAttribute.from(getAttribute("position")).getX());
  }


}

