package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.List;

public class PlayerListAttribute extends GameConstructListAttribute {

  public PlayerListAttribute(String key, List<String> playerIds) {
    super(key, playerIds);
  }
}
