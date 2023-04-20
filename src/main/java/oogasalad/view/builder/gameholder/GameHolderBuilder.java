package oogasalad.view.builder.gameholder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.Player;
import oogasalad.view.builder.board.ImmutableBoardInfo;

public class GameHolderBuilder {

  BBoard board;
  Optional<List<Player>> players;
  GameObjectType gameObjectType;
//   Optional<List<Card>> cards;

  public GameHolderBuilder setPlayers(Optional<List<Player>> players) {
    this.players = players;
    this.gameObjectType = GameObjectType.PLAYER;
    return this;
  }

//  public void setCards(List<Card> cards) {
//    this.cards = Optional.of(cards);
//    this.gameObjectType = GameObjectType.CARD;
//  }

  public GameHolderBuilder setBoard(BBoard board) {
    this.board = board;
    this.gameObjectType = GameObjectType.BOARD;
    return this;
  }

  public GameHolder build(){
    GameHolder holder = new GameHolder();
    holder.board = this.board;
    holder.players = this.players;
//    holder.cards = this.cards;

//    GameHolder gameholder = setBoard(board).setPlayers(players).build();
    return holder;
  }
}




///**
// * https://howtocodetutorial.wordpress.com/generic-builder-pattern-in-java-8/
// * https://www.sayem.org/generic-builder-pattern/
// * @param <T>
// */
//
//public class GameHolderBuilder<T> {
//  private T instance;
//  private boolean ifCond = true; // default
//  public GameHolderBuilder(Class<T> clazz) {
//    try {
//      instance = clazz.newInstance();
//    } catch (InstantiationException | IllegalAccessException e) {
//      e.printStackTrace();
//    }
//  }
////public class GameHolderBuilder<T extends GameHolder> {
////  private T instance;
////
////  public GameHolderBuilder(Class<T> clazz) {
////
////  }
//
//  BBoard board;
//  Optional<List<Player>> players;
//  GameObjectType gameObjectType;
//
//  // Optional<List<Card>> cards;
//
//  public GameHolderBuilder(){
//
//  }
//
//  public GameHolderBuilder<T> with(Consumer<T> setter) {
//    if (ifCond) setter.accept(instance);
//    return this;
//  }
//
//  public T get(){
//    return instance;
//  }
//
//  public static <T> GameHolderBuilder<T> build(Class<T> clazz) {
//    return new GameHolderBuilder<>(clazz);
//  }
//
//  public GameHolderBuilder<T> If(BooleanSupplier condition){
//    this.ifCond = condition.getAsBoolean();
//    return this;
//  }
//
//  public GameHolderBuilder<T> endIf(){
//    this.ifCond = true;
//    return this;
//  }
//
//
//  private final Supplier<T> instantiator;
//
//  private List<Consumer<T>> instanceModifiers = new ArrayList<>();
//
//  public GameHolderBuilder(Supplier<T> instantiator) {
//    this.instantiator = instantiator;
//  }
//
//  public static <T> GameHolderBuilder<T> of(Supplier<T> instantiator) {
//    return new GameHolderBuilder<T>(instantiator);
//  }
//
//  public <U> GameHolderBuilder<T> with(BiConsumer<T, U> consumer, U value) {
//    Consumer<T> c = instance -> consumer.accept(instance, value);
//    instanceModifiers.add(c);
//    return this;
//  }
//
//  public T build() {
//    T value = instantiator.get();
//    instanceModifiers.forEach(modifier -> modifier.accept(value));
//    instanceModifiers.clear();
//    return value;
//  }