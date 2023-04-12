package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;

public class Player extends GameConstruct {

  @Inject
  @JsonCreator
  public Player(
      @JsonProperty("type") String schemaName,
      SchemaDatabase database) {
    super(schemaName, database);
  }
}
