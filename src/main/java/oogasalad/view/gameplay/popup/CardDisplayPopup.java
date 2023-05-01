package oogasalad.view.gameplay.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CardDisplayPopup {

  private final Stage stage;
  private final BorderPane root;
  private final String cardTitle;
  private final String cardDescription;

  public CardDisplayPopup(String title, String description) {
    this.stage = new Stage();
    this.root = new BorderPane();
    cardTitle = title;
    cardDescription = description;
    createPopupContent();
  }

  private void createPopupContent() {
    Label cardLabel = new Label(cardDescription);
    cardLabel.setPadding(new Insets(10));
    cardLabel.setAlignment(Pos.CENTER);
    root.setCenter(cardLabel);

    HBox bottomBox = new HBox();
    bottomBox.setPadding(new Insets(10));
    bottomBox.setAlignment(Pos.CENTER);
    root.setBottom(bottomBox);

    Scene scene = new Scene(root, 300, 200);
    stage.setScene(scene);
    stage.setTitle(cardTitle);
  }

  public void showCard() {
    stage.showAndWait();
  }
}
