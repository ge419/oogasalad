package oogasalad.view.gameplay.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * A popup window that displays a card title and description to the user.
 *
 * <p>
 * This popup window is designed to display a card title and description to the user. It creates a new
 * {@link Stage} and a {@link BorderPane} that holds a label with the description of the card. The
 * title of the card is displayed in the window's title bar. The user can view the card by calling
 * {@link #showCard()}.
 * <p>
 *
 * Assumptions:
 * <ul>
 * <li>The card title and description passed to the constructor are not null.</li>
 * <li>The length of the card title is less than or equal to the length of the title bar.</li>
 * </ul>
 *
 * <p>
 * Dependencies:
 * <ul>
 * <li>{@link javafx.geometry.Insets}</li>
 * <li>{@link javafx.geometry.Pos}</li>
 * <li>{@link javafx.scene.Scene}</li>
 * <li>{@link javafx.scene.control.Label}</li>
 * <li>{@link javafx.scene.layout.BorderPane}</li>
 * <li>{@link javafx.scene.layout.HBox}</li>
 * <li>{@link javafx.stage.Stage}</li>
 * </ul>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * CardDisplayPopup popup = new CardDisplayPopup("Card Title", "Card description goes here.");
 * popup.showCard();
 * }
 * </pre>
 *
 * @author ajo24
 */
public class CardDisplayPopup {
  private final Stage stage;
  private final BorderPane root;
  private final String cardTitle;
  private final String cardDescription;

  /**
   * Creates a new CardDisplayPopup instance with the given card title and description.
   *
   * @param title       the title of the card being displayed.
   * @param description the description of the card being displayed.
   */
  public CardDisplayPopup(String title, String description) {
    this.stage = new Stage();
    this.root = new BorderPane();
    cardTitle = title;
    cardDescription = description;
    createPopupContent();
  }

  /**
   * Creates the content of the popup window.
   */
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

  /**
   * Displays the popup window to the user.
   */
  public void showCard() {
    stage.showAndWait();
  }
}