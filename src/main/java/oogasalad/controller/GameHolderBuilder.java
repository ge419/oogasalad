package oogasalad.controller;

import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Players;

// TODO: The builder should probably not be a separate class...
public class GameHolderBuilder {

  //TODO: default values for game object

  BBoard board;
  Players players;
//  GameObjectType gameObjectType;
//   Optional<List<Card>> cards;

  public GameHolderBuilder setPlayers(Players players) {
    this.players =  players;
//    this.gameObjectType = GameObjectType.PLAYER;
    return this;
  }

//  public void setCards(List<Card> cards) {
//    this.cards = Optional.of(cards);
//    this.gameObjectType = GameObjectType.CARD;
//  }

  public GameHolderBuilder setBoard(BBoard board) {
    this.board = board;
//    this.gameObjectType = GameObjectType.BOARD;
    return this;
  }

  public GameHolder build() {
    GameHolder holder = new GameHolder();

//    holder.board = this.board;
//    holder.players = this.players;
//    holder.cards = this.cards;
    return holder;
  }
}