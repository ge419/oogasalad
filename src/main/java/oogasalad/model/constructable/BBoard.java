package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class BBoard {

  private final List<Tile> tiles;
  private final List<Player> players;

  public BBoard() {
    this.tiles = new ArrayList<>();
    this.players = new ArrayList<>();
  }

  @JsonCreator
  public BBoard(@JsonProperty("tiles") List<Tile> t) {
    this.tiles = new ArrayList<>(t);
    this.players = new ArrayList<>();
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  public int getTileCount() {
    return this.tiles.size();
  }

  public List<Player> getPlayers() {
    return players;
  }

  public int getPlayerCount() {
    return this.players.size();
  }

  public void addTile(Tile t) {
    this.tiles.add(t);
  }

  public Tile getById(String id) {
    for (Tile t: tiles) {
      if (t.getId().equals(id)) return t;
    }
    return null;
  }

  public void addPlayer(Player p) {
    this.players.add(p);
  }

  public void addAllPlayers(List<Player> playerList) {
    for (Player p : playerList) {
      this.players.add(p);
    }
  }

  public Player getPlayerById(String id) {
    for (Player p: players) {
      if (p.getId().equals(id)) return p;
    }
    return null;
  }
}
