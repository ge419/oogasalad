package oogasalad.view.builder.gameholder;

import java.util.List;
import java.util.Optional;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.view.builder.board.ImmutableBoardInfo;

public class GameHolderBuilder {
  BBoard board;
  Optional<List<Player>> players;
  GameObjectType gameObjectType;

  // Optional<List<Card>> cards;
  public GameHolderBuilder(){}
  public GameHolderBuilder setPlayers(List<Player> players) {
    this.players = Optional.of(players);
    this.gameObjectType = GameObjectType.PLAYER;
    return this;
  }

//  public void setCards(List<T> cards) {
//    this.cards = Optional.of(cards);
//    this.gameObjectType = GameObjectType.CARD;
//  }

  public GameHolderBuilder setBoard(BBoard board) {
    this.board = board;
    this.gameObjectType = GameObjectType.BOARD;
    return this;
  }

//  public String setThing()
  public GameHolder getGameHolder(){
    return new GameHolder();
  }
//  List<Player> thing  = null;
//  GameHolder a  = new GameHolderBuilder().setPlayers(thing)..setgetGameHolder();


}
