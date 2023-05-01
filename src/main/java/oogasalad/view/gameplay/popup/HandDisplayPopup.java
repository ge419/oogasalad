package oogasalad.view.gameplay.popup;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oogasalad.view.tiles.ViewTile;

public class HandDisplayPopup extends BorderPane {

  private final Stage stage;

  public HandDisplayPopup() {
    stage = new Stage();
  }

  public void createScene(Parent root) {
    Scene scene = new Scene(root);
    stage.setScene(scene);

    stage.setOnHidden(event -> {
      hideHand();
    });
  }

  public void addCards(List<ViewTile> cardList) {
    HBox hand = new HBox();
    for (ViewTile card : cardList) {
      hand.getChildren().add((Node) card);
      hand.setAlignment(Pos.CENTER);
    }
    ScrollPane scrollPane = new ScrollPane(hand);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportWidth(stage.getWidth());
    scrollPane.setPrefViewportHeight(stage.getHeight());
    BorderPane.setAlignment(scrollPane, Pos.CENTER);
    this.setCenter(scrollPane);
    createScene(this);
  }

  public void showHand() {
    stage.show();
  }

  public void hideHand() {
    stage.close();
  }
}