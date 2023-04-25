package oogasalad.view.gameplay.popup;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import oogasalad.view.gameplay.pieces.Cards;

public class CardDisplayPopup extends Popups {

  private Popup popup;

  public CardDisplayPopup(Cards card) {
    super();
    popup = getNewPopup();
    Cards dummyCard = new Cards(card.getImageURL());
    ((BorderPane)popup.getContent().get(0)).setCenter(dummyCard);
    popup.setOnHidden(event -> {
      hideHand();
    });
    popup.setAutoHide(true);
    popup.setHideOnEscape(true);
  }
  @Override
  public void showHand(Node anchor, Point2D offset) {
    double sceneWidth = anchor.getScene().getWindow().getWidth();
    double sceneHeight = anchor.getScene().getWindow().getHeight();
    double popupX = sceneWidth / 4 + offset.getX();
    double popupY = sceneHeight / 4 + offset.getY();
    popup.show(anchor.getScene().getWindow(), popupX, popupY);
  }

  @Override
  public void hideHand() {
    popup.hide();
  }
}
