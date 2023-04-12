package oogasalad.view.builder.panefeatures;

import javafx.beans.property.BooleanProperty;
import javafx.scene.input.MouseEvent;

/**
 * The dragger will handle drag operations of any Node passed to it. This was heavily inspired by:
 * https://edencoding.com/drag-shapes-javafx/ which was written by Ed Eden-Rump. Thank you!
 *
 * @author tmh85
 */
public interface DraggerAPI {

  /**
   * Sets whether we can drag this object or not.
   *
   * @param dragStatus boolean that, if true, will enable us to drag the object
   */
  void setDraggable(boolean dragStatus);

  /**
   * Returns the draggable property that dictates whether we can drag or not.
   *
   * @return DragProperty
   */
  BooleanProperty getDragProperty();

  /**
   * Returns a boolean version of DragProperty. Convenience method.
   *
   * @return true if draggable, false if not.
   */
  boolean getDragStatus();

  /**
   * Checks if a button (or combination) is pressed for a current event handler.
   * @param e MouseEvent handler
   * @return true if button is pressed, false if not.
   */
  default boolean checkIfDragButtonIsPressed(MouseEvent e){
    return e.isPrimaryButtonDown();
  }

}
