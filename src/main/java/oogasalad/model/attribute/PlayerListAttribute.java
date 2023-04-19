package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.List;

public class PlayerListAttribute extends Attribute {

  @JsonProperty
  private List<String> playerIds;
  @JsonCreator
  public PlayerListAttribute(@JsonProperty("key") String key,
      @JsonProperty("ids") List<String> playerIds) {
    super(key);
    this.playerIds = new ArrayList<>(playerIds);
  }

  @JsonGetter("ids")
  public List<String> getPlayerIds(){return playerIds;}

  @JsonSetter("ids")
  public void setPlayerIds(List<String> playerIds){this.playerIds = playerIds;}
}
