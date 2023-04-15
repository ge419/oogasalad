package oogasalad.view.tiles;

import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.attribute.PositionAttribute;
import oogasalad.model.constructable.Tile;

public class RenderStrategy {

  protected void renderTile(Tile BTile, BorderPane pane, List<ViewTile> tileList) {
    switch (BTile.getType()) {
      case "street" -> {
        StreetTile tile = new StreetTile(BTile.getId(), BTile.getCoordinate(),
            BTile.getColor(), BTile.getInfo(), BTile.getWidth(), BTile.getHeight());
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
      case "image" -> {
        ImageTile tile =  new ImageTile(BTile.getId(), BTile.getCoordinate(),
            BTile.getImage(), BTile.getInfo(), BTile.getWidth(), BTile.getHeight());
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
      default -> {
        BasicTile tile = new BasicTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
    }
  }
}
