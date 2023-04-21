package oogasalad.controller;

import java.util.List;
import java.util.Optional;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;

public class GameHolder {

  BBoard board;
  Optional<List<Player>> players;

  public BBoard getBoard() {
    return board;
  }
  public Optional<List<Player>> getPlayers() {
    return players;
  }
}
