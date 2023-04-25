package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.model.engine.rules.Rule;

public class GameHolder {

  private BBoard board;
  private Players players;
  private Player currentPlayer;
  private final ListProperty<Rule> rules = new SimpleListProperty<>(
      FXCollections.observableArrayList());

  public BBoard getBoard() {
    return board;
  }

  public void setBoard(BBoard board) {
    this.board = board;
  }

  @JsonIgnore
  public Players getPlayers() {
    return players;
  }

  @JsonIgnore
  public void setPlayers(Players players) {
    this.players = players;
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

  public ListProperty<Rule> getRules() {
    return rules;
  }

  public void setRules(List<Rule> rules) {
    this.rules.setAll(rules);
  }

  public static GameHolder createDefaultGame() {
    GameHolder gameHolder = new GameHolder();
    gameHolder.setBoard(new BBoard());
    gameHolder.setPlayers(new Players());
    return gameHolder;
  }
}
