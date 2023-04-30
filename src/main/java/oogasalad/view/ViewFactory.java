package oogasalad.view;

import java.util.List;
import oogasalad.controller.GameController;
import oogasalad.model.constructable.Tile;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.gameplay.pieces.Cards;
import oogasalad.view.tiles.Tiles;

public interface ViewFactory {

  Gameview makeGameview(GameController controller);

  Tiles makeTiles(List<Tile> backendTiles);

  Cards makeCards(List<Tile> backendTiles);
}
