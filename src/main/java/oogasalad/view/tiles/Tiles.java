package oogasalad.view.tiles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import oogasalad.view.Coordinate;
import oogasalad.view.Renderable;

public class Tiles implements Renderable {

  @Override
  public void render(BorderPane pane) {
    File file = new File("data/example/tiles_2.json");
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode rootNode = objectMapper.readTree(file);

      JsonNode tilesNode = rootNode.get("tiles");

      for (int i = 0; i < tilesNode.size(); i++) {
        JsonNode tileNode = tilesNode.get(i);

        // TODO: get rid of if else statement and use factory to retrieve appropriate tile constructor
        if (tileNode.get("type").asText().equals("street")) {
          int id = tileNode.get("id").asInt();
          double[] position = objectMapper.treeToValue(tileNode.get("coordinate"), double[].class);
          Coordinate tileCoord = new Coordinate(position[0], position[1]);
          String colorStr = tileNode.get("color").asText();
          Color tileColor = Color.web(colorStr);
          String streetName = tileNode.get("name").asText();
          String price = tileNode.get("price").asText();
          int tileWidth = tileNode.get("width").asInt();
          int tileHeight = tileNode.get("height").asInt();
          StreetTile streetTile = new StreetTile(id, tileCoord, tileColor, streetName, price, tileWidth, tileHeight);
          pane.getChildren().add(streetTile);
        }

        else if (tileNode.get("type").asText().equals("image")) {
          int id = tileNode.get("id").asInt();
          double[] position = objectMapper.treeToValue(tileNode.get("coordinate"), double[].class);
          Coordinate tileCoord = new Coordinate(position[0], position[1]);
          String imgPath = tileNode.get("image").asText();
          String textStr = tileNode.get("text").asText();
          int tileWidth = tileNode.get("width").asInt();
          int tileHeight = tileNode.get("height").asInt();
          Map<String, String> textMap = new HashMap<>();
          textMap.put("text", textStr);

          ImageTile imageTile = new ImageTile(id, tileCoord, imgPath, textMap, tileWidth, tileHeight);
          pane.getChildren().add(imageTile);
        }

        else if (tileNode.get("type").asText().equals("utility")) {
          int id = tileNode.get("id").asInt();
          double[] position = objectMapper.treeToValue(tileNode.get("coordinate"), double[].class);
          Coordinate tileCoord = new Coordinate(position[0], position[1]);
          String imgPath = tileNode.get("image").asText();
          String name = tileNode.get("name").asText();
          String price = tileNode.get("price").asText();
          int tileWidth = tileNode.get("width").asInt();
          int tileHeight = tileNode.get("height").asInt();

          Map<String, String> textMap = new HashMap<>();
          textMap.put("name", name);
          textMap.put("price", price);
          ImageTile utilityTile = new ImageTile(id, tileCoord, imgPath, textMap, tileWidth, tileHeight);
          pane.getChildren().add(utilityTile);
        }

        else {
          int id = tileNode.get("id").asInt();
          double[] position = objectMapper.treeToValue(tileNode.get("position"), double[].class);
          int[] next = objectMapper.treeToValue(tileNode.get("next"), int[].class);
          int[] onLand = objectMapper.treeToValue(tileNode.get("onLand"), int[].class);
          int[] afterTurn = objectMapper.treeToValue(tileNode.get("afterTurn"), int[].class);
          BasicTile tile = new BasicTile(id, position, next, onLand, afterTurn);
          tile.setId("Tiles");
          pane.getChildren().add(tile);
        }

      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
