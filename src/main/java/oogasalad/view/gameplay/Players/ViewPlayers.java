package oogasalad.view.gameplay.Players;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
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

  private static final double ARBITRARY_YPADDING = 10;
  private static final double ARBITRARY_XSCALE = 1.2;
  private static final double ARBITRARY_YSCALE = 0.85;
  private static final double FULL_TURN = 360;
  private static final double SCREEN_WIDTH = 1500;
  private static final double SCREEN_HEIGHT = 1000;
  private final List<PlayerUI> playerList = new ArrayList<>();
  Players BPlayers;
  private final ViewFactory viewFactory;
  private final GameHolder game;

  public ViewPlayers(Players p, ViewFactory viewFactory, GameHolder game) {
    this.BPlayers = p;
    this.viewFactory = viewFactory;
    this.game = game;
  }

  /**
   * Receive the frontend player component that is associated with the backend player with specified
   * ID
   *
   * <p>Assumptions: ID passed is valid.
   *
   * <p>Parameters:
   *
   * @param id is the ID of the backend player
   *
   *           <p>Exceptions:
   * @return PlayerUI associated with backend player
   * @throws IllegalArgumentException if the ID passed is invalid (no player with specified ID)
   *
   *                                  <p>Other details: None
   */
  public PlayerUI getPlayer(String id) {
    for (PlayerUI player : playerList) {
      if (player.getPlayerId().equals(id)) {
        return player;
      }
    }
    throw new IllegalArgumentException("No player with id " + id);
  }

  /**
   * @see Renderable
   */
  @Override
  public void render(BorderPane pane) {
    for (int i = 0; i < BPlayers.getList().size(); i++) {
      Coordinate playerCoordinate = playerPosition(i, BPlayers.getList().size(), SCREEN_WIDTH,
          SCREEN_HEIGHT);
      PlayerUI playerUI = new PlayerUI(BPlayers.getList().get(i), playerCoordinate, viewFactory,
          game);
      playerList.add(playerUI);
      pane.getChildren().add(playerUI);
    }
  }

  private Coordinate playerPosition(int playerNo, int totalNo, double screenWidth,
      double screenHeight) {
    double centerX = screenWidth / 2;
    double centerY = screenHeight / 2 - ARBITRARY_YPADDING;
    double radius = Math.min(screenWidth, screenHeight) / 2;
    double angle = FULL_TURN / totalNo;
    double panelX, panelY;
    double panelAngle = playerNo * angle + FULL_TURN / 2;
    panelX = centerX + ARBITRARY_XSCALE * radius * Math.sin(Math.toRadians(panelAngle));
    panelY = centerY - ARBITRARY_YSCALE * radius * Math.cos(Math.toRadians(panelAngle));
    return new Coordinate(panelX, panelY, panelAngle + FULL_TURN / 2);
  }
}
