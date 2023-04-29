package oogasalad.view.gameplay.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.BorderPane;
import javax.swing.text.html.Option;
import oogasalad.model.constructable.Players;
import oogasalad.view.Coordinate;
import oogasalad.view.Renderable;

public class ViewPlayers implements Renderable {

  Optional<Players> BPlayers;
  private final List<PlayerUI> playerList = new ArrayList<>();

  public ViewPlayers(Optional<Players> p) {
    this.BPlayers = p;
  }

  public PlayerUI getPlayer(String id) {
    for (PlayerUI player : playerList) {
      if (player.getPlayerId().equals(id)) {
        return player;
      }
    }
    throw new IllegalArgumentException("No player with id " + id);
  }

  @Override
  public void render(BorderPane pane) {
      for (int i = 0; i < BPlayers.get().getPlayers().size(); i++) {
        //TODO: take in backend player
        Coordinate playerCoordinate = playerPosition(i, BPlayers.get().getPlayers().size(), 1500, 1000);
        PlayerUI playerUI = new PlayerUI(BPlayers.get().getPlayers().get(i), playerCoordinate);
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
