package oogasalad.view.tiles;

import oogasalad.model.constructable.Tile;

public class ViewTileFactory {

  public ViewTile createTile(String tileType, Tile BTile) {
    switch (tileType) {
      // TODO: once we implement schema for other tile types, uncomment
//      case "street" -> {
//        return new StreetTile(BTile.getAttribute("id"), BTile.getAttribute("coordinate"),
//            BTile.getAttribute("color"), BTile.getAttribute("name"), BTile.getAttribute("price"), BTile.getAttribute("width"), BTile.getAttribute("height"));
//      }
//      case "image" -> {
//        Map<String, String> textMap = new HashMap<>();
//        //TODO: create text map
//        return new ImageTile(BTile.getAttribute("id"), BTile.getAttribute("coordinate"),
//            BTile.getAttribute("imgPath"), textMap, BTile.getAttribute("width"), BTile.getAttribute("height"));
//      }
      default -> {
        return new BasicTile(BTile);
      }
    }
  }
}
