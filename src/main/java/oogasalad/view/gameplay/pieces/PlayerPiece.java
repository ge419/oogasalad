package oogasalad.view.gameplay.pieces;

import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;

public class PlayerPiece extends GamePiece {

  private String playerName;
  private Tile currentTile;

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

  public void moveToTile(Tile tile) {
    this.currentTile = tile;
    moveDirectly(tile.getCoordinate(), this);
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

}
