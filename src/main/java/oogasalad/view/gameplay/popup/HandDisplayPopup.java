package oogasalad.view.gameplay.popup;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Popup;
import oogasalad.view.gameplay.pieces.Cards;

public class HandDisplayPopup extends Popups {

  private final Cards[] cards;
  private Popup popup;
  private FlowPane hand;

  public HandDisplayPopup(Cards[] cards) {
    super();
    this.cards = cards;
    popup = getPopup();
    hand = new FlowPane();
    for (Cards card : cards) {
      hand.getChildren().add(card);
    }
    for (Cards card: cards) {
      ((BorderPane)popup.getContent().get(0)).setCenter(card);
    }

    popup.setOnHidden(event -> {
      hideHand();
    });
    popup.setAutoHide(true);
    popup.setHideOnEscape(true);
  }

  public void setSelected(boolean selected) {
    // TODO: add a hover feature to each individual card to make it seens as "selected"
    for (Cards card : cards) {
      card.setSelected(selected);
    }
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
