package oogasalad.view;

import javafx.scene.layout.BorderPane;

/**
 * <p> Interface that is implemented by all frontend classes that need to be rendered onto the
 * gameplay board.
 *
 * <p>Assumptions: Assumes that the root that the frontend component is to be added to is a BorderPane
 *
 * <p>Dependencies: None
 *
 * <p>Example usage:
 * <pre>{@code
 * tiles.render(UIroot);
 * }</pre>
 *
 * <p>Other details: None
 * @author Woonggyu wj61
 */

public interface Renderable {

  /**
   * Renders the component onto the window
   *
   * <p>Assumptions: Root that the component is rendered to is a JavaFX BorderPane
   *
   * <p>Parameters:
   * @param pane the BorderPane that the board is to be added to
   *
   * <p>Exceptions: none
   *
   * <p>Other details: None
   */
  void render(BorderPane pane);

  /**
   * Removes the component from the window
   *
   * <p>Assumptions: Root that the component is rendered to is a JavaFX BorderPane
   *
   * <p>Parameters:
   * @param pane the BorderPane that the board is to be added to
   *
   * <p>Exceptions: none
   *
   * <p>Other details: None
   */
  default void clear(BorderPane pane) {
    pane.getChildren().remove(this);
  }
}
