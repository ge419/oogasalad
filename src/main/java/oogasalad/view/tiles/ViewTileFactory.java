package oogasalad.view.tiles;

import oogasalad.model.constructable.Tile;
import oogasalad.view.builder.customTile.CustomTileRendering;

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

  public abstract ImageTile createImageTile(Tile tile);

  public abstract BasicTile createBasicTile(Tile tile);

  public abstract StreetTile createStreetTile(Tile tile);

  public abstract CustomTileRendering createCustomTile(Tile tile);

  public abstract ViewTile createDynamicViewTile(Tile tile);
}
