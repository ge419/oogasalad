package oogasalad.view.gameplay.Players;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;
import oogasalad.view.ViewFactory;
import oogasalad.view.gameplay.pieces.Cards;
import oogasalad.view.gameplay.popup.HandDisplayPopup;
import oogasalad.view.tiles.ViewTile;

/**
 * <p> JavaFX rendering of Player UI (information such as Player's score, icon, color)
 *
 * <p>Assumptions: None
 *
 * <p>Dependencies: Textable, Backgroundable, Imageable interfaces, Player object, Coordinate
 * object
 *
 * @author Woonggyu wj61
 */

public class PlayerUI extends StackPane implements Textable, Backgroundable, Imageable {

  private static final double PLAYER_WIDTH = 200;
  private static final double PLAYER_HEIGHT = 50;
  private static final double USERNAME_TEXT_SCALE = 2.5;
  private static final double SCORE_TEXT_SCALE = 3;
  private static final double IMAGE_SCALE = 6;
  private static final double MARKER_WIDTH = 10;
  private static final Color MARKER_COLOR = Color.YELLOW;
  private static final Color MARKER_FILL = Color.TRANSPARENT;
  private static final Color UI_STROKE_COLOR = Color.BLACK;
  private final Player modelPlayer;
  private final BooleanProperty current;
  private HandDisplayPopup popup;
  private final ViewFactory viewFactory;
  private final GameHolder game;

  public PlayerUI(Player BPlayer, Coordinate coordinate, ViewFactory viewFactory, GameHolder game) {
    modelPlayer = BPlayer;
    current = modelPlayer.getCurrentAttribute().valueProperty();

    this.game = game;
    this.viewFactory = viewFactory;
    this.setPrefSize(PLAYER_WIDTH, PLAYER_HEIGHT);
    this.setLayoutX(coordinate.getXCoor());
    this.setLayoutY(coordinate.getYCoor());
    this.getTransforms().add(new Rotate(coordinate.getAngle(), Rotate.Z_AXIS));
    ImageView playerIcon = createImage(PLAYER_WIDTH, BPlayer.getImage(), IMAGE_SCALE);

    getChildren().addAll(
        createBackground(PLAYER_WIDTH, PLAYER_HEIGHT, Color.web(BPlayer.getColor()),
            UI_STROKE_COLOR),
        playerIcon,
        createTextBox(List.of(BPlayer.getName(), BPlayer.getScore()), PLAYER_HEIGHT, PLAYER_WIDTH));
    setMargin(playerIcon, new Insets(0, PLAYER_WIDTH / 2, 0, 0));

    markCurrentPlayer();
    this.setOnMouseClicked(this::displayPlayerHand);
  }

  private void displayPlayerHand(MouseEvent event) {
    if (popup != null) {
      popup.hideHand();
    }
    popup = new HandDisplayPopup();
    //TODO: only pass in Player cards
    List<Tile> tiles = new ArrayList<>();
    for (Tile tile : game.getBoard().getTiles()) {
      if (tile.getOwnerId().equals(modelPlayer.getId())) {
        tiles.add(tile);
      }
    }
    Cards cards = viewFactory.makeCards(tiles);
    cards.render(popup);
    List<ViewTile> cardList = cards.getCardList();
    popup.addCards(cardList);
    popup.showHand();
  }


  /**
   * @see Textable
   */
  @Override
  public VBox createTextBox(List info, double height, double width) {
    VBox textBox = new VBox();

    Text playerName = new Text(info.get(0).toString());
    playerName.textProperty().bind(modelPlayer.getNameAttribute().valueProperty());
    resizeText(playerName, height, USERNAME_TEXT_SCALE, width);
    playerName.setLayoutY(this.getLayoutY());
    Text scoreText = new Text(info.get(1).toString());
    scoreText.textProperty().bind(modelPlayer.getScoreAttribute().valueProperty().asString());
    resizeText(scoreText, height, SCORE_TEXT_SCALE, width);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(playerName, scoreText);
    return textBox;
  }

  /**
   * Receive the ID of the backend player associated with the frontend player component
   *
   * <p>Assumptions: None
   *
   * <p>Parameters: None
   *
   * <p>Exceptions: None, as a frontend player UI is always created with a backend player as an
   * argument.
   *
   * <p>Other details: None
   *
   * @return The ID of backend player
   */
  public String getPlayerId() {
    return this.modelPlayer.getId();
  }

  private void markCurrentPlayer() {
    Rectangle marker = new Rectangle((int) PLAYER_WIDTH, (int) PLAYER_HEIGHT);
    marker.setStroke(MARKER_COLOR);
    marker.setStrokeWidth(MARKER_WIDTH);
    marker.setFill(MARKER_FILL);
    current.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        getChildren().add(marker);
      } else {
        getChildren().remove(marker);
      }
    });
  }
}
