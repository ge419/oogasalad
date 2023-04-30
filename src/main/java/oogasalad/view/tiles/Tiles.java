package oogasalad.view.tiles;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;

public class Tiles implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> tileList = new ArrayList<>();
  private final TileRenderStrategy renderStrategy;

  @Inject
  public Tiles(@Assisted List<Tile> t, TileRenderStrategy renderStrategy) {
    this.BTiles = t;
    this.renderStrategy = renderStrategy;
  }

  @Override
  public void render(BorderPane pane) {
    for (Tile bTile : BTiles) {
      ViewTile viewTile = renderStrategy.createViewTile(bTile);
      pane.getChildren().add(viewTile.asNode());
      tileList.add(viewTile);
    }
  }

  public ViewTile getTile(String id) {
    for (ViewTile tile : tileList) {
      if (tile.getTileId().equals(id)) {
        return tile;
      }
    }
    throw new IllegalArgumentException("No tile with id " + id);
  }
}
