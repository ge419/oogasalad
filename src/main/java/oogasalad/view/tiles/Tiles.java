package oogasalad.view.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;

public class Tiles implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> tileList = new ArrayList<>();

  public Tiles(ArrayList<Tile> t) {
    this.BTiles = t;
  }

  @Override
  public void render(BorderPane pane) {
    for (Tile t : BTiles) {
      BasicTile tile = new BasicTile(t);
      tile.setId("Tiles");
      pane.getChildren().add(tile);
      tileList.add(tile);
    }
  }

  public ViewTile getTile(int id) {
    for (ViewTile tile : tileList) {
      if (tile.getTileId() == id) return tile;
    }
    throw new IllegalArgumentException("No tile with id " + id);
  }
}
