package oogasalad.view.gameplay.pieces;

import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.Movable;
import oogasalad.view.tiles.StreetTile;

public class StreetCard extends StreetTile implements Movable {

  public StreetCard(Tile BTile) {
    super(BTile);
  }


  @Override
  public void move(Coordinate[] coorArray) {

  }

  public void setSelected(boolean selected) {
    if (selected) {
      setTranslateY(getTranslateY() - 10);
    } else {
      setTranslateY(getTranslateY() + 10);
    }
  }

}
