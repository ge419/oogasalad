package oogasalad.view.builder.gameholder;

import java.util.List;
import java.util.Optional;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.view.builder.board.ImmutableBoardInfo;

public class GameHolderBuilder {
  BBoard board;
  Optional<List<Player>> players;
  // Optional<List<Card>> cards;

  public GameHolderBuilder setPlayers(Optional<List<Player>> players) {
    this.players = players;
    return this;
  }

//  public void setCards(List<T> cards) {
//    this.cards = Optional.of(cards);
//  }

  public GameHolderBuilder setCards(List<Player> players){
    this.players = Optional.of(players);
    return this;
  }

  public GameHolderBuilder setBoard(BBoard board) {
    this.board = board;
    return this;
  }
  public GameHolder getGameHolder(){
    return new GameHolder();
  }

}
