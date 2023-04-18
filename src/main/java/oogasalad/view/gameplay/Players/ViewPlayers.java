package oogasalad.view.gameplay.Players;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import oogasalad.model.constructable.Player;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Renderable;

public class ViewPlayers implements Renderable {

  List<Player> BPlayers;
  private final List<PlayerUI> playerList = new ArrayList<>();

  public ViewPlayers(ArrayList<Player> p) {
    this.BPlayers = p;
  }

  //TODO: delete empty constructor after backend player
  public ViewPlayers() {
  }

  public PlayerUI getPlayer(int id) {
    for (PlayerUI player : playerList) {
      if (player.getPlayerId() == id) {
        return player;
      }
    }
    throw new IllegalArgumentException("No player with id " + id);
  }

  @Override
  public void render(BorderPane pane) {
    for (int i = 0; i < 4; i++) {
//    for (int i = 0; i < BPlayers.size(); i++) {
      //TODO: take in backend player
//      Coordinate playerCoordinate = playerPosition(i, BPlayers.size(), 1500, 1000);
      Coordinate playerCoordinate = playerPosition(i, 4, 1500, 1000);
      PlayerUI playerUI = new PlayerUI(i, "Player " + i, 500, playerCoordinate,
          "view.gameplay/piece_1.png", Color.ORANGE);
      playerList.add(playerUI);
      pane.getChildren().add(playerUI);
    }
  }

  private Coordinate playerPosition(int playerNo, int totalNo, double screenWidth,
      double screenHeight) {
    double centerX = screenWidth / 2;
    double centerY = screenHeight / 2 - 10;
    // arbitrary padding; change later
    double radius = Math.min(screenWidth, screenHeight) * 0.5;
    double angle = 360.0 / totalNo;
    double panelX, panelY;
    double panelAngle = playerNo * angle + 180;
    panelX = centerX + 1.2 * radius * Math.sin(Math.toRadians(panelAngle));
    panelY = centerY - 0.9 * radius * Math.cos(Math.toRadians(panelAngle));
    return new Coordinate(panelX, panelY, panelAngle + 180);
  }
}
