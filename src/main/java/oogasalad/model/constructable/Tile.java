package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.AttributeList;

public class Tile extends Constructable {

  @Inject
  @JsonCreator
  public Tile(
      @JsonProperty("type") String schemaName,
      SchemaDatabase database,
      @JsonProperty("attributes") AttributeList attributeList) {
    super(schemaName, database, attributeList);
  }
}

