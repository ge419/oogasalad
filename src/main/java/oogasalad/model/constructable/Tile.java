package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import javax.inject.Inject;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.attribute.SchemaDatabase;

public class Tile extends GameConstruct {

  public static final String BASE_SCHEMA_NAME = "basicTile";

  @Inject
  public Tile(@JacksonInject SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
  }

  @Override
  public String toString() {
    return "Tile={ "
    + PositionAttribute.from(getAttribute("position")).toString()
        + " }";
  }


}

