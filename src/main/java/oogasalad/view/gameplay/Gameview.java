package oogasalad.view.gameplay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.BasicTile;

public class Gameview {

  //TODO: refactor to read from property file
  private final int VIEW_WIDTH = 1200;
  private final int VIEW_HEIGHT = 800;

  @JsonProperty("board")
  public String myBoardPath;

  @JsonProperty("choice")
  public String choice;

  public void renderGameview(Stage primaryStage) {
    BorderPane UIroot = new BorderPane();

    for (BasicTile tile : renderTiles()){
      UIroot.getChildren().add(tile);
    }
    UIroot.setCenter(renderBoard());

    UIroot.getChildren().add(renderDie());

    for (PlayerPiece piece : renderPieces()) {
      UIroot.getChildren().add(piece);
    }

    Scene scene = new Scene(UIroot);
    //TODO: refactor to read from property file
    primaryStage.setTitle("Monopoly");
    primaryStage.setScene(scene);
    primaryStage.setHeight(VIEW_HEIGHT);
    primaryStage.setWidth(VIEW_WIDTH);
    primaryStage.show();
  }

  private List<BasicTile> renderTiles() {
    File file = new File("data/example/tiles_1.json");
    ObjectMapper objectMapper = new ObjectMapper();
    List<BasicTile> tiles = new ArrayList<>();
    try {
      JsonNode rootNode = objectMapper.readTree(file);

      JsonNode tilesNode = rootNode.get("tiles");

      for (int i = 0; i < tilesNode.size(); i++) {
        JsonNode tileNode = tilesNode.get(i);
        int id = tileNode.get("id").asInt();
        double[] position = objectMapper.treeToValue(tileNode.get("position"), double[].class);
        int[] next = objectMapper.treeToValue(tileNode.get("next"), int[].class);
        int[] onLand = objectMapper.treeToValue(tileNode.get("onLand"), int[].class);
        int[] afterTurn = objectMapper.treeToValue(tileNode.get("afterTurn"), int[].class);

        BasicTile tile = new BasicTile(id, position, next, onLand, afterTurn);
        tiles.add(tile);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return tiles;
  }

  private Board renderBoard() {
    return new Board(myBoardPath);
  }
  private Die renderDie() {
    Die die = new Die();
    return die;
  }

  private List<PlayerPiece> renderPieces() {
    //TODO: add logic that decides how many pieces are parsed and with what images
    List<PlayerPiece> piecesList = new ArrayList<>();
    piecesList.add(new PlayerPiece("data/example/piece_1.png", "Bob"));
    return piecesList;
  }

}
