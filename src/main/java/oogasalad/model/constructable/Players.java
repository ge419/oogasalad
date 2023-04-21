package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Players {
  private List<Player> players;

  public Players() {
    this.players = new ArrayList<>();
  }

  @JsonCreator
  public Players(@JsonProperty("players") List<Player> p) {
    this.players = new ArrayList<>(p);
  }

  public List<Player> getPlayers() {
    return players;
  }
  public void setPlayers(List<Player> players) {this.players = players;}

  @JsonIgnore
  public Player getById(String id) {
    for (Player p: players) {
      if (p.getId().equals(id)) return p;
    }
    return null;
  }
}
