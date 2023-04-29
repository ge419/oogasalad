package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  @JsonIgnore
  public Optional<Player> getById(String id) {
    for (Player p : players) {
      if (p.getId().equals(id)) {
        return Optional.of(p);
      }
    }

    return Optional.empty();
  }

  @JsonIgnore
  public Optional<Piece> getPieceById(String id) {
    for (Player player : players) {
      for (Piece p : player.getPieces()) {
        if (p.getId().equals(id)) {
          return Optional.of(p);
        }
      }
    }

    return Optional.empty();
  }
}
