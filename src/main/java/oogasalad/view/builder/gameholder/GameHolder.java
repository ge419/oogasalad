package oogasalad.view.builder.gameholder;

import java.util.List;
import java.util.Optional;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.graphs.ImmutableGraph;


public class GameHolder {

  BBoard board;
  Optional<List<Player>> players;
  // Optional<List<Card>> cards;

  public GameHolder() {

  }

  public BBoard getBoard() {
    return board;
  }
  public Optional<List<Player>> getPlayers() {
    return players;
  }
//  public Optional<List<Card>> getCards() {
//    return cards;
//  }
}