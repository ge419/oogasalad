package oogasalad.view.gameplay.pieces;

 import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.input.MouseEvent;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.gameplay.Movable;
import oogasalad.view.gameplay.popup.CardDisplayPopup;
import oogasalad.view.tiles.StreetTile;

public class StreetCard extends StreetTile implements Movable {

  @Inject
  public StreetCard(@Assisted Tile BTile) {
    super(BTile);
    setMouseTransparent(false);
    this.setOnMouseClicked(this::createCardPopup);
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
