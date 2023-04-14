package oogasalad.view.tiles;

import java.util.ArrayList;
import java.util.List;
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
    ViewTileFactory viewTileFactory = new ViewTileFactory();
    for (Tile t : BTiles) {
      BasicTile tile = (BasicTile) viewTileFactory.createTile("tile id", t);
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
