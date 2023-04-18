package oogasalad.view.gameplay;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.view.Backgroundable;
import oogasalad.view.Renderable;
import oogasalad.view.Textable;

public class ViewPlayer extends StackPane implements Textable, Backgroundable, Renderable {

  private double PLAYER_WIDTH = 200;
  private double PLAYER_HEIGHT = 50;
  private double USERNAME_TEXT_SCALE = 2.5;
  private double SCORE_TEXT_SCALE = 3;

  public ViewPlayer(String username, double score) {
    this.setPrefSize(PLAYER_WIDTH, PLAYER_HEIGHT);
    this.setLayoutX(750);
    this.setLayoutY(950);
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


  @Override
  public void render(BorderPane pane) {
    pane.getChildren().add(this);
  }
}
