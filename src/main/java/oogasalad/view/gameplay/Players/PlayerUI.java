package oogasalad.view.gameplay.Players;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Textable;

public class PlayerUI extends StackPane implements Textable, Backgroundable {

  private double PLAYER_WIDTH = 200;
  private double PLAYER_HEIGHT = 50;
  private double USERNAME_TEXT_SCALE = 2.5;
  private double SCORE_TEXT_SCALE = 3;
  private int id;

  //TODO: take in backend player once implemented
  public PlayerUI(int id, String username, double score, Coordinate coordinate) {
    this.id = id;
    this.setPrefSize(PLAYER_WIDTH, PLAYER_HEIGHT);

    //TODO: calculate based on height and width of gameview
    this.setLayoutX(coordinate.getXCoor());
    this.setLayoutY(coordinate.getYCoor());
    this.getTransforms().add(new Rotate(coordinate.getAngle(), Rotate.Z_AXIS));

    getChildren().addAll(tileBackground(PLAYER_WIDTH, PLAYER_HEIGHT),
        createTextBox(username + "," + Double.toString(score), PLAYER_HEIGHT, PLAYER_WIDTH));
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
