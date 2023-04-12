package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.AttributeList;

public class Player extends Constructable {

  @Inject
  @JsonCreator
  public Player(
      @JsonProperty("type") String schemaName,
      SchemaDatabase database,
      @JsonProperty("attributes") AttributeList attributeList) {
    super(schemaName, database, attributeList);
  }
}
