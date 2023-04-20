package oogasalad.view.gameplay.popup;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;

public abstract class Popups {

  private Popup popup;

  public Popups() {
    initPopup();
  }

  private void initPopup() {
    popup = new Popup();
    BorderPane contentPane = new BorderPane();
    contentPane.setStyle("-fx-background-color: transparent;");
    BorderPane.setAlignment(contentPane, Pos.CENTER);
    BorderPane root = new BorderPane(contentPane);
    root.setPrefWidth(800);
    root.setPrefHeight(400);
    root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    popup.getContent().add(root);
  }

  protected Popup getPopup() {
    initPopup();
    return popup;
  }
  public abstract void show(Node anchor, Point2D offset);

  public void hide() {
    popup.hide();
  }

  public boolean isShowing() {
    return popup.isShowing();
  }
}

