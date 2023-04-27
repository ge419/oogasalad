package oogasalad.view.gameplay.Players;

import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import oogasalad.model.constructable.Player;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;

public class PlayerUI extends StackPane implements Textable, Backgroundable, Imageable {

  private static final double PLAYER_WIDTH = 200;
  private static final double PLAYER_HEIGHT = 50;
  private static final double USERNAME_TEXT_SCALE = 2.5;
  private static final double SCORE_TEXT_SCALE = 3;
  private static final double IMAGE_SCALE = 6;
  private static final Color UI_STROKE_COLOR = Color.BLACK;
  private final Player modelPlayer;

  //TODO: take in backend player once implemented
  public PlayerUI(Player BPlayer, Coordinate coordinate) {
    modelPlayer = BPlayer;

    this.setPrefSize(PLAYER_WIDTH, PLAYER_HEIGHT);

    this.setLayoutX(coordinate.getXCoor());
    this.setLayoutY(coordinate.getYCoor());
    this.getTransforms().add(new Rotate(coordinate.getAngle(), Rotate.Z_AXIS));

    ImageView playerIcon = createImage(PLAYER_WIDTH, BPlayer.getImage(), IMAGE_SCALE);

    getChildren().addAll(
     createBackground(PLAYER_WIDTH, PLAYER_HEIGHT, Color.web(BPlayer.getColor()), UI_STROKE_COLOR),
        playerIcon,
        createTextBox(BPlayer.getName() + "," + Double.toString(BPlayer.getScore()), PLAYER_HEIGHT, PLAYER_WIDTH));
    this.setMargin(playerIcon, new Insets(0, PLAYER_WIDTH / 2, 0, 0));
  }

  @Override
  public VBox createTextBox(String info, double height, double width) {
    VBox textBox = new VBox();
    String[] infoList = info.split(",");

    Text playerName = new Text(infoList[0]);
    resizeText(playerName, height, USERNAME_TEXT_SCALE, width);
    playerName.setLayoutY(this.getLayoutY());
    Text scoreText = new Text(infoList[1]);
    scoreText.textProperty().bind(modelPlayer.getScoreAttribute().valueProperty().asString());
    resizeText(scoreText, height, SCORE_TEXT_SCALE, width);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(playerName, scoreText);
    return textBox;
  }

  public String getPlayerId() {
    return this.modelPlayer.getId();
  }

//  private void initPlayerUI() {
//
//    modelPlayer.setImage();
//    player2.setColor("00FF00");
//  }
}
