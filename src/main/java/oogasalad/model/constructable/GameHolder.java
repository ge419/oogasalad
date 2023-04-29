package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.controller.GameInfo;
import oogasalad.model.engine.rules.Rule;

public class GameHolder {

  private int minPlayerNum = 1;
  private int maxPlayerNum = 4;
  private GameInfo gameInfo;
  private BBoard board;
  private Optional<Players> players;
  private Player currentPlayer;
  private Optional<List<Piece>> pieces;
  private final ListProperty<Rule> rules = new SimpleListProperty<>(
      FXCollections.observableArrayList());

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
  public int minPlayer() {
    return this.minPlayerNum;
  }

  @JsonIgnore
  public int maxPlayer() {
    return this.maxPlayerNum;
  }

  @JsonIgnore
  public void setPieces(List<Piece> pieces) {
    this.pieces = Optional.ofNullable(pieces);
  }

  @JsonIgnore
  public Optional<List<Piece>> getPieces() {
    return this.pieces;
  }

  @JsonIgnore
  public Optional<Players> getPlayers() {
    return this.players;
  }

  @JsonIgnore
  public void setPlayers(Players players) {
    this.players = Optional.ofNullable(players);
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
    return players.get().getById(id);
  }

  public Optional<Tile> getTileById(String id) {
    return board.getById(id);
  }

  public Optional<Piece> getPieceById(String id) {
    return players.get().getPieceById(id);
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

  public static GameHolder createDefaultGame() {
    GameHolder gameHolder = new GameHolder();
    gameHolder.setGameInfo(new GameInfo());
    gameHolder.setBoard(new BBoard());
    gameHolder.setPlayers(new Players());
    return gameHolder;
  }
}
