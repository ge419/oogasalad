package oogasalad.view.gameplay.Players;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
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


  private int id;

  //TODO: take in backend player once implemented
  public PlayerUI(int id, String username, double score, Coordinate coordinate, String imagePath,
      Color playerColor) {
    this.id = id;
    this.setPrefSize(PLAYER_WIDTH, PLAYER_HEIGHT);

    //TODO: calculate based on height and width of gameview
    this.setLayoutX(coordinate.getXCoor());
    this.setLayoutY(coordinate.getYCoor());
    this.getTransforms().add(new Rotate(coordinate.getAngle(), Rotate.Z_AXIS));

    ImageView playerIcon = createImage(PLAYER_WIDTH, imagePath, IMAGE_SCALE);

    getChildren().addAll(
        createBackground(PLAYER_WIDTH, PLAYER_HEIGHT, playerColor, UI_STROKE_COLOR),
        createTextBox(username + "," + Double.toString(score), PLAYER_HEIGHT, PLAYER_WIDTH),
        playerIcon);
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
    resizeText(scoreText, height, SCORE_TEXT_SCALE, width);
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().addAll(playerName, scoreText);
    return textBox;
  }


  public void incrementScore(double amount) {
    VBox textBox = (VBox) getChildren().get(1);
    Text scoreText = (Text) textBox.getChildren().get(1);
    double currScore = Double.parseDouble(scoreText.getText());
    scoreText.setText(Double.toString(currScore + amount));
  }

  public void decrementScore(double amount) {
    VBox textBox = (VBox) getChildren().get(1);
    Text scoreText = (Text) textBox.getChildren().get(1);
    double currScore = Double.parseDouble(scoreText.getText());
    scoreText.setText(Double.toString(currScore - amount));
  }

  public int getPlayerId() {
    return id;
  }
}
