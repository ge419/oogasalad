package oogasalad.view.gameplay.pieces;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.Nodeable;
import oogasalad.view.gameplay.Movable;
//import oogasalad.view.gameplay.popup.CardDisplayPopup;
import oogasalad.view.gameplay.popup.CardDisplayPopup;
import oogasalad.view.tiles.ImageTile;

public class ImageCard extends ImageTile implements Movable, Nodeable {

// private final Tile BTile;

  public ImageCard(Tile BTile) {
    super(BTile);
    setMouseTransparent(false);
    this.setOnMouseClicked(this::createCardPopup);
  }

  private void createCardPopup(MouseEvent mouseEvent) {
    String title = StringAttribute.from(this.getTile().getAttribute("info").get()).getValue();
    String description = StringAttribute.from(this.getTile().getAttribute("description").get()).getValue();
    System.out.println(title + " " + description);
    CardDisplayPopup cardPopup = new CardDisplayPopup(title, description);
    cardPopup.showCard();
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
