package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.attribute.ColorAttribute;
import oogasalad.model.attribute.ColorMetadata;

public class BBoard {

  private final List<Tile> tiles;
  private final List<Player> players;

  public BBoard() {
    this.tiles = new ArrayList<>();
    this.players = new ArrayList<>();
  }


  @JsonCreator
  public BBoard(
      @JsonProperty("tiles") List<Tile> t,
      @JsonProperty("players") List<Player> p
  ) {
    this.tiles = new ArrayList<>(t);
    this.players = new ArrayList<>(p);
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  @JsonIgnore
  public int getTileCount() {
    return this.tiles.size();
  }

  public List<Player> getPlayers() {
    return players;
  }

  @JsonIgnore
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
