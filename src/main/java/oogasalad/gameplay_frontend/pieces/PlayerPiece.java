package oogasalad.gameplay_frontend.pieces;

import oogasalad.gameplay_frontend.Coordinate;

public class PlayerPiece extends GamePiece {
  private String playerName;

  public PlayerPiece(String imageURL, String playerName) {
    super(imageURL);
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void changePlayerName(String newName) {
    this.playerName = newName;
  }

  @Override
  public void move(Coordinate[] coorArray) {

  }

  @Override
  public void moveDirectly(Coordinate coor) {

  }

}
