package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.TileAttribute;

public class Player extends AbstractGameConstruct {

  public static final String BASE_SCHEMA_NAME = "basicPlayer";
  public static final String POSITION_ATTRIBUTE = "currentTile";
  public static final String NAME_ATTRIBUTE = "name";

  @Inject
  public Player(
      SchemaDatabase database) {
    super(BASE_SCHEMA_NAME, database);
  }

  @JsonIgnore
  public TileAttribute tileAttribute() {
    return TileAttribute.from(getAttribute(POSITION_ATTRIBUTE));
  }

  @JsonIgnore
  public StringAttribute nameAttribute() {
    return StringAttribute.from(getAttribute(NAME_ATTRIBUTE));
  }

  @JsonIgnore
  public String getCurrentTile() {
    return tileAttribute().getId();
  }

  @JsonIgnore
  public String getName() {
    return nameAttribute().getValue();
  }

  @JsonIgnore
  public void setName(String newName) {
    nameAttribute().setValue(newName);
  }

  @JsonIgnore
  public void moveToTile(String id) {
    tileAttribute().setId(id);
  }
}
