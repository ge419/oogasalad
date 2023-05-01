package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.List;

public class GameConstructListAttribute extends AbstractAttribute {

  @JsonProperty
  private List<String> gameConstructIds;

  @JsonCreator
  public GameConstructListAttribute(@JsonProperty("key") String key,
      @JsonProperty("ids") List<String> playerIds) {
    super(key);
    this.gameConstructIds = new ArrayList<>(playerIds);
  }

  @JsonGetter("ids")
  public List<String> getGameConstructIds() {
    return gameConstructIds;
  }

  @JsonSetter("ids")
  public void setGameConstructIds(List<String> gameConstructIds) {
    this.gameConstructIds = gameConstructIds;
  }
}

