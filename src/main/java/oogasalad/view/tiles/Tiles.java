package oogasalad.view.tiles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import oogasalad.view.Coordinate;
import oogasalad.view.Renderable;

public class Tiles implements Renderable {

  @Override
  public void render(BorderPane pane) {
    File file = new File("data/example/tiles_1.json");
    ObjectMapper objectMapper = new ObjectMapper();
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
        tile.setId("Tiles");
        pane.getChildren().add(tile);
      }
      StreetTile tile = new StreetTile(1, new Coordinate(200,200), Color.BLUE,  "Park Place", "$350", 80, 120);
      pane.getChildren().add(tile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
