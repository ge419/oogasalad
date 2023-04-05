package oogasalad.gameplay_frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.gameplay_frontend.Tiles.BasicTile;

public class Gameview{
  //TODO: refactor to read from property file
  private final int VIEW_WIDTH = 1200;
  private final int VIEW_HEIGHT = 800;

  @JsonProperty("board")
  private String board;

  @JsonProperty("choice")
  private String choice;
  private List<BasicTile> tiles;

  public void renderGameview() {
    Stage primaryStage = new Stage();
    BorderPane UIroot = new BorderPane();
    Scene scene = new Scene(UIroot);

    //TODO: refactor to read from property file
    primaryStage.setTitle("Monopoly");
    primaryStage.setScene(scene);
    primaryStage.setHeight(VIEW_HEIGHT);
    primaryStage.setWidth(VIEW_WIDTH);
    primaryStage.show();
  }

  private List<BasicTile> renderTiles() {
    return null;
  }

  private Board renderBoard() {
    return null;
  }

}
