package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.TileAttribute;

public class Player extends AbstractGameConstruct {

  public static final String BASE_SCHEMA_NAME = "basicPlayer";
  public static final String POSITION_ATTRIBUTE = "currentTile";

  @Inject
  @JsonCreator
  public Player(
      @JsonProperty("type") String schemaName,
      SchemaDatabase database) {
    super(schemaName, database);
  }

  public TileAttribute tileAttribute() {
    return TileAttribute.from(getAttribute(POSITION_ATTRIBUTE));
  }

  public String getCurrentTile() {
    return tileAttribute().getId();
  }

  public void moveToTile(String id) {
    tileAttribute().setId(id);
  }
}
