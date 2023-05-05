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

/**
 * Represents a popup window that displays a list of cards in the user's hand. This class provides
 * functionality for creating and displaying a scene with a scrollable list of cards, as well as
 * showing and hiding the popup window.
 *
 * <p>Assumptions:
 * <ul>
 * <li>The list of cards provided to this class will not be null.</li>
 * <li>The dimensions of the popup window will be determined by the size of the contents inside of it.</li>
 * </ul>
 *
 * <p>Dependencies:
 * <ul>
 * <li>{@link javafx.geometry.Pos}</li>
 * <li>{@link javafx.scene.Node}</li>
 * <li>{@link javafx.scene.Parent}</li>
 * <li>{@link javafx.scene.Scene}</li>
 * <li>{@link javafx.scene.control.ScrollPane}</li>
 * <li>{@link javafx.scene.layout.BorderPane}</li>
 * <li>{@link javafx.scene.layout.HBox}</li>
 * <li>{@link javafx.stage.Stage}</li>
 * <li>{@link oogasalad.view.tiles.ViewTile}</li>
 * </ul>
 *
 * <p>Example Usage:
 * <pre>{@code
 * HandDisplayPopup popup = new HandDisplayPopup();
 * popup.addCards(cardList);
 * popup.showHand();
 * }</pre>
 *
 * @author ajo24
 */
public class HandDisplayPopup extends BorderPane {

  private final Stage stage;

  /**
   * Initializes a new instance of the HandDisplayPopup class.
   */
  public HandDisplayPopup() {
    stage = new Stage();
  }

  /**
   * Creates and sets the scene for this popup window with the given root node.
   *
   * @param root the root node of the scene
   */
  public void createScene(Parent root) {
    Scene scene = new Scene(root);
    stage.setScene(scene);

    stage.setOnHidden(event -> {
      hideHand();
    });
  }

  /**
   * Adds the given list of ViewTiles to the scrollable list in this popup window.
   *
   * @param cardList the list of ViewTiles to be added to the scrollable list
   */
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

  /**
   * Shows the popup window.
   */
  public void showHand() {
    stage.show();
  }

  /**
   * Hides the popup window.
   */
  public void hideHand() {
    stage.close();
  }
}