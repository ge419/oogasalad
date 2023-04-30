package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.controller.GameInfo;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.observers.GameObserver;
import oogasalad.model.observers.Observable;

@Singleton
public class GameHolder implements Observable<GameObserver> {
  private GameInfo gameInfo;
  private BBoard board;
  private Players players;
  private Player currentPlayer;
  private List<Piece> pieces;
  private final ListProperty<Rule> rules = new SimpleListProperty<>(
      FXCollections.observableArrayList());

  private final List<GameObserver> observers = new ArrayList<>();

  public GameHolder() {
    setGameInfo(new GameInfo());
    setBoard(new BBoard());
    setPlayers(new Players());
    pieces = new ArrayList<>();
  }

  public GameInfo getGameInfo() {
    return gameInfo;
  }

  public void setGameInfo(GameInfo gameInfo) {
    this.gameInfo = gameInfo;
  }

  public BBoard getBoard() {
    return board;
  }

  public void setBoard(BBoard board) {
    this.board = board;
  }

  @JsonIgnore
  public void setPieces(List<Piece> pieces) {
    this.pieces = pieces;
  }

  @JsonIgnore
  public List<Piece> getPieces() {
    return this.pieces;
  }

  @JsonIgnore
  public Players getPlayers() {
    return this.players;
  }

  @JsonIgnore
  public void setPlayers(Players players) {
    this.players = players;
  }

  @JsonIgnore
  public void removePlayers(List<Player> playersList) {
    this.players.getList().removeAll(playersList);
    this.notifyRemoval(playersList);
  }

  @JsonIgnore
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @JsonIgnore
  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
  }

  public Optional<Player> getPlayerById(String id) {
    return players.getById(id);
  }

  public Optional<Tile> getTileById(String id) {
    return board.getById(id);
  }

  public Optional<Piece> getPieceById(String id) {
    return players.getPieceById(id);
  }


  public ListProperty<Rule> rulesProperty() {
    return rules;
  }

  @JsonGetter("rules")
  public List<Rule> getRules() {
    return rules;
  }

  @JsonSetter("rules")
  public void setRules(List<Rule> rules) {
    this.rules.setAll(rules);
  }

  @Override
  public void register(GameObserver observer) {
    this.observers.add(observer);
  }

  @Override
  public void remove(GameObserver observer) {
    this.observers.remove(observer);
  }

  @Override
  public void notifyList() {
    for (GameObserver observer : observers) {
      observer.updateOnPlayers(this.players);
      observer.updateOnPieces(this.pieces);
    }
  }

  public void notifyRemoval(List<Player> players) {
    for (GameObserver observer : observers) {
      observer.updateOnPlayerRemoval(players);
    }
  }
}
