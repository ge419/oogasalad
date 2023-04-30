package oogasalad.view.gameplay.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Renderable;
import oogasalad.view.tiles.RenderStrategy;
import oogasalad.view.tiles.ViewTile;

public class Cards implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> cardList = new ArrayList<>();

  public Cards(List<Tile> t) {
    this.BTiles = t;
  }

  @Override
  public void render(BorderPane pane) {
    RenderStrategy renderStrategy = new RenderStrategy();
    for (Tile bTile : BTiles) {
      renderStrategy.renderCard(bTile, pane, cardList);
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
