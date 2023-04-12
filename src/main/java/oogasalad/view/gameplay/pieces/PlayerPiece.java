package oogasalad.view.gameplay.pieces;

import oogasalad.view.Coordinate;
import oogasalad.view.tiles.ViewTile;

public class PlayerPiece extends GamePiece{
  private String playerName;
  private ViewTile currentTile;

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

  public void moveToTile(ViewTile tile) {
    this.currentTile = tile;
    moveDirectly(new Coordinate((int)(double)tile.getPosition()[0], (int)(double)tile.getPosition()[1]));
  }

  public ViewTile getCurrentTile() {
    return currentTile;
  }

  @Override
  public void moveDirectly(Coordinate coor) {
    this.setLayoutX(coor.getXCoor());
    this.setLayoutY(coor.getYCoor());
  }

}
