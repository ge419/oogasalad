package oogasalad.view;

import javafx.scene.layout.BorderPane;

public interface Renderable {

  void render(BorderPane pane);

  default void clear(BorderPane pane) {
    pane.getChildren().remove(this);
  }
}
