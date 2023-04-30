package oogasalad.view.gameplay.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CardDisplayPopup {

  private Stage stage;
  private BorderPane root;

  public CardDisplayPopup(String cardText) {
    this.stage = new Stage();
    this.root = new BorderPane();

    // Add the card text to a Label
    Label cardLabel = new Label(cardText);
    cardLabel.setPadding(new Insets(10));
    cardLabel.setAlignment(Pos.CENTER);

    // Add the Label to the center of the BorderPane
    root.setCenter(cardLabel);

    // Add a close button to the bottom of the BorderPane
    HBox bottomBox = new HBox();
    bottomBox.setPadding(new Insets(10));
    bottomBox.setAlignment(Pos.CENTER);
    root.setBottom(bottomBox);

    Label closeButton = new Label("Close");
    closeButton.setStyle("-fx-background-color: lightgray; -fx-padding: 5;");
    closeButton.setOnMouseClicked(event -> {
      stage.close();
    });
    bottomBox.getChildren().add(closeButton);

    // Set the scene and show the stage
    Scene scene = new Scene(root, 300, 200);
    stage.setScene(scene);
    stage.setTitle("Card Display");
  }

  public void showCard() {
    stage.showAndWait();
  }
}
