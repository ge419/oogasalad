package oogasalad.controller;

import java.util.List;
import java.util.Optional;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;

public class GameHolder {

  BBoard board;
  Players players;

  public BBoard getBoard() {
    return board;
  }
  public Players getPlayers() {
    return players;
  }
  public Player getPlayer() { return players.getPlayers().get(0); }
}
