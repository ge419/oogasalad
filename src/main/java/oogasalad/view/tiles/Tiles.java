package oogasalad.view.tiles;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;

public class Tiles implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> tileList = new ArrayList<>();

  public Tiles(List<Tile> t) {
    this.BTiles = t;
  }

  @Override
  public void render(BorderPane pane) {
    RenderStrategy renderStrategy = new RenderStrategy();
    for (Tile bTile : BTiles) {
      renderStrategy.renderTile(bTile, pane, tileList);
    }
  }


  public ViewTile getTile(String id) {
    for (ViewTile tile : tileList) {
      if (tile.getCompId().equals(id)) {
        return tile;
      }
    }
    throw new IllegalArgumentException("No tile with id " + id);
  }
}
