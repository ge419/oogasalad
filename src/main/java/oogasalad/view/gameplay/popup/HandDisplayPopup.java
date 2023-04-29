package oogasalad.view.gameplay.popup;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oogasalad.view.gameplay.pieces.Cards;
import oogasalad.view.gameplay.pieces.ImageCard;
import oogasalad.view.tiles.ViewTile;

public class HandDisplayPopup extends BorderPane {

//  private final Cards[] cards;
  private final Stage stage;

  public HandDisplayPopup() {
    stage = new Stage();
//    createScene(this);
  }

  public void createScene(Parent root) {
    Scene scene = new Scene(root);
    stage.setScene(scene);

    stage.setOnHidden(event -> {
      hideHand();
    });
  }

//  public void setSelected(boolean selected) {
//    // TODO: add a hover feature to each individual card to make it seens as "selected"
//    for (ImageCard card : cards) {
//      card.setSelected(selected);
//    }
//  }

  public void addCards(List<ViewTile> cardList) {
    HBox hand = new HBox();
    for (ViewTile card: cardList) {
      hand.getChildren().add((Node) card);
      hand.setAlignment(Pos.CENTER);
    }
    Group group = new Group(hand);
    setCenter(group);
    createScene(this);
  }

  public void showHand(Node anchor, Point2D offset) {
    double sceneWidth = anchor.getScene().getWindow().getWidth();
    double sceneHeight = anchor.getScene().getWindow().getHeight();
    double popupX = sceneWidth + offset.getX();
    double popupY = sceneHeight + offset.getY();
    stage.setX(popupX);
    stage.setY(popupY);
    stage.show();
  }

  public void hideHand() {
    stage.close();
  }
}
