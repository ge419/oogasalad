package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GameHolder {

  private BBoard board;
  private Players players;
  private Player currentPlayer;

  public BBoard getBoard() {
    return board;
  }

  public void setBoard(BBoard board) {
    this.board = board;
  }

  public Players getPlayers() {
    return players;
  }

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

  public static GameHolder createDefaultGame() {
    GameHolder gameHolder = new GameHolder();
    gameHolder.setBoard(new BBoard());
    gameHolder.setPlayers(new Players());
    return gameHolder;
  }
}
