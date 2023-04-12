package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;

public class Tile extends AttributeObject {

  public static final String BASE_SCHEMA_NAME = "basicTile";

  @Inject
  public Tile(SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
    // TODO tile types
  }


}

