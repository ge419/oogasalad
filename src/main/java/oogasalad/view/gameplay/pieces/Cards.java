package oogasalad.view.gameplay.pieces;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;
import oogasalad.view.tiles.TileRenderStrategy;
import oogasalad.view.tiles.ViewTile;

public class Cards implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> cardList = new ArrayList<>();
  private final TileRenderStrategy renderStrategy;

  @Inject
  public Cards(@Assisted List<Tile> t, TileRenderStrategy renderStrategy) {
    this.BTiles = t;
    this.renderStrategy = renderStrategy;
  }

  @Override
  public void render(BorderPane pane) {
    for (Tile bTile : BTiles) {
      ViewTile viewTile = renderStrategy.createViewTile(bTile);
      pane.getChildren().add(viewTile.asNode());
      cardList.add(viewTile);
    }
  }

  public List<ViewTile> getCardList() {
    return cardList;
  }

  public ViewTile getCard(String id) {
    for (ViewTile card : cardList) {
      if (card.getTileId().equals(id)) {
        return card;
      }
    }
    throw new IllegalArgumentException("No card with id " + id);
  }
}
