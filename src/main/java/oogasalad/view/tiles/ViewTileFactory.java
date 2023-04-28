package oogasalad.view.tiles;

import java.util.Map;
import java.util.function.Function;
import oogasalad.model.constructable.Tile;

public interface ViewTileFactory {
  public abstract ImageTile createImageTile(Tile tile);
  public abstract BasicTile createBasicTile(Tile tile);
  public abstract StreetTile createStreetTile(Tile tile);
  public abstract ViewTile createDynamicViewTile(Tile tile);
}
