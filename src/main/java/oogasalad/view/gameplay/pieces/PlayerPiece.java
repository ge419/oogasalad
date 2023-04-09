package oogasalad.view.gameplay.pieces;

import oogasalad.view.Coordinate;
import oogasalad.view.tiles.Tile;

public class PlayerPiece extends GamePiece{
  private String playerName;
  private Tile currentTile;

  public PlayerPiece(String imageURL, String playerName) {
    super(imageURL);
    this.playerName = playerName;

    setOnMouseClicked(event -> {
      //TODO: remove this and implement a button in GameView that passes in a coordinate
      Coordinate coor = new Coordinate(300, 300);
      moveDirectly(coor);
    });

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
    moveDirectly(new Coordinate((int) tile.getPosition()[0], (int) tile.getPosition()[1]));
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

  @Override
  public void moveDirectly(Coordinate coor) {
    this.setLayoutX(coor.getXCoor());
    this.setLayoutY(coor.getYCoor());
  }

}
