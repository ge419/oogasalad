package oogasalad.view.gameplay.Players;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javax.swing.text.View;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Players;
import oogasalad.view.Coordinate;
import oogasalad.view.Renderable;
import oogasalad.view.ViewFactory;

/**
 * <p> Class that holds all PlayerUI objects to be rendered
 *
 * <p>Assumptions: Number of players is not ridiculously large.
 *
 * <p>Dependencies: Renderable interface, Player object, Coordinate object
 *
 * @author Woonggyu wj61
 */

public class ViewPlayers implements Renderable {

  Players BPlayers;
  private final List<PlayerUI> playerList = new ArrayList<>();
  private ViewFactory viewFactory;
  private GameHolder game;

  public ViewPlayers(Players p, ViewFactory viewFactory, GameHolder game) {
    this.BPlayers = p;
    this.viewFactory = viewFactory;
    this.game = game;
  }

  /**
   * Receive the frontend player component that is associated with the backend player with specified ID
   *
   * <p>Assumptions: ID passed is valid.
   *
   * <p>Parameters:
   * @param id is the ID of the backend player
   *
   * <p>Exceptions:
   * @throws IllegalArgumentException if the ID passed is invalid (no player with specified ID)
   *
   * <p>Other details: None
   *
   * @return PlayerUI associated with backend player
   */
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
      for (int i = 0; i < BPlayers.getList().size(); i++) {
        Coordinate playerCoordinate = playerPosition(i, BPlayers.getList().size(), 1500, 1000);
        PlayerUI playerUI = new PlayerUI(BPlayers.getList().get(i), playerCoordinate, viewFactory, game);
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
    panelY = centerY - 0.85 * radius * Math.cos(Math.toRadians(panelAngle));
    return new Coordinate(panelX, panelY, panelAngle + 180);
  }
}
