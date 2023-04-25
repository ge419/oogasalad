package oogasalad.view.gameplay.popup;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import oogasalad.view.Holdable;

public abstract class Popups implements Holdable {

  private Popup popup;

  public Popups() {
    popup = initPopup();
  }

  private Popup initPopup() {
    popup = new Popup();
    BorderPane contentPane = new BorderPane();
    contentPane.setStyle("-fx-background-color: transparent;");
    BorderPane.setAlignment(contentPane, Pos.CENTER);
    BorderPane root = new BorderPane(contentPane);
    root.setPrefWidth(800);
    root.setPrefHeight(400);
    root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    popup.getContent().add(root);
    return popup;
  }

  protected Popup getNewPopup() {
    return initPopup();
  }

  protected Popup getPopup() {
    return popup;
  }

  public void hide() {
    popup.hide();
  }

  public boolean isShowing() {
    return popup.isShowing();
  }
}