package oogasalad.view.tiles;

import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.customTile.CustomTileFrontEnd;
import oogasalad.view.gameplay.pieces.ImageCard;
import oogasalad.view.gameplay.pieces.StreetCard;

/**
 * <p>Together with the Guice's dependency injection, this factory will create the tiles
 * listed below in it's methods.</p>
 * <p>This is used in the ViewTileWrapper class to create any tile based on a backend tile.</p>
 * <p>Designwise this was done in order to allow any tile to be created without having
 * to manually put in if statements when creating a new ViewTile.</p>
 *
 * @author tmh85
 * @author dcm67
 */
public interface ViewTileFactory {

  ImageTile createImageTile(Tile tile);

  BasicTile createBasicTile(Tile tile);

  StreetTile createStreetTile(Tile tile);

  CustomTileFrontEnd createCustomTile(Tile tile);

  ViewTile createDynamicViewTile(Tile tile);

  StreetCard createStreetCard(Tile tile);
  ImageCard createImageCard(Tile tile);
}
