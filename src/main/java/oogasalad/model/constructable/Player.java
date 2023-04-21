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
  public Player(
      SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
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
