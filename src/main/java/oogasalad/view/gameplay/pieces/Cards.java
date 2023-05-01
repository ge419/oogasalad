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

/**
 * This class represents a collection of cards, each of which is a visual representation of a tile
 * in the game. It provides a method for rendering the cards on a JavaFX BorderPane.
 *
 * @author ajo24
 */
public class Cards implements Renderable {

  private final List<Tile> BTiles;
  private final List<ViewTile> cardList = new ArrayList<>();
  private final TileRenderStrategy renderStrategy;

  /**
   * Constructs a new Cards object with the given list of tiles and render strategy.
   *
   * @param t              the list of tiles to be rendered as cards
   * @param renderStrategy the strategy to use for rendering the cards
   * @throws NullPointerException if either t or renderStrategy is null
   */
  @Inject
  public Cards(@Assisted List<Tile> t, TileRenderStrategy renderStrategy) {
    if (t == null || renderStrategy == null) {
      throw new NullPointerException("The list of tiles and render strategy cannot be null.");
    }
    this.BTiles = t;
    this.renderStrategy = renderStrategy;
  }

  /**
   * Renders the cards on the given JavaFX BorderPane using the render strategy specified in the
   * constructor.
   *
   * @param pane the BorderPane on which to render the cards
   * @throws NullPointerException if pane is null
   */
  @Override
  public void render(BorderPane pane) {
    if (pane == null) {
      throw new NullPointerException("The BorderPane cannot be null.");
    }
    for (Tile bTile : BTiles) {
      ViewTile viewTile = renderStrategy.createCard(bTile);
      pane.getChildren().add(viewTile.asNode());
      cardList.add(viewTile);
    }
  }

  /**
   * Returns a list of ViewTiles representing the cards in this Cards object.
   *
   * @return a list of ViewTiles representing the cards in this Cards object
   */
  public List<ViewTile> getCardList() {
    return cardList;
  }
}
