package oogasalad.view.gameplay.pieces;

import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.Movable;
//import oogasalad.view.gameplay.popup.CardDisplayPopup;
import oogasalad.view.tiles.ImageTile;

public class ImageCard extends ImageTile implements Movable {

//  private final String imageURL;

  public ImageCard(Tile BTile) {
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
