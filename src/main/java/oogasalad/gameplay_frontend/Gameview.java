package oogasalad.gameplay_frontend;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Gameview extends Application {

  //TODO: refactor to read from property file
  private final int VIEW_WIDTH = 1200;
  private final int VIEW_HEIGHT = 800;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    BorderPane UIroot = new BorderPane();

    Board gameboard = new Board();

    UIroot.setCenter(gameboard.render());

    Scene scene = new Scene(UIroot);

    //TODO: refactor to read from property file
    primaryStage.setTitle("Monopoly");
    primaryStage.setScene(scene);
    primaryStage.setHeight(VIEW_HEIGHT);
    primaryStage.setWidth(VIEW_WIDTH);
    primaryStage.show();
  }
}
