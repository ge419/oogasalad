package gameplay_frontend;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**

 An interface representing all frontend components that can be clicked by the user. This interface
 defines the properties and behavior of clickable components in the game, including the methods
 for setting event handlers to be executed when the user interacts with the component using the
 mouse.
 <p>
 Implementation details:
 <p>
 setOnMouseClicked(EventHandler<MouseEvent> eventHandler) - This method sets an event handler to
 be executed when the user clicks on the component.
 <p>
 setOnMouseEntered(EventHandler<MouseEvent> eventHandler) - This method sets an event handler to
 be executed when the user's mouse pointer enters the component.
 <p>
 setOnMouseExited(EventHandler<MouseEvent> eventHandler) - This method sets an event handler to
 be executed when the user's mouse pointer exits the component.
 <p>
 These methods allow the game logic to handle user interactions with clickable components in a
 consistent and predictable way, without needing to know the specifics of each individual
 component. Any class that implements this interface should provide these methods to allow for
 easy handling of user events on the component.
 */

public interface Clickable {
  public void setOnMouseClicked(EventHandler<MouseEvent> eventHandler);
  public void setOnMouseEntered(EventHandler<MouseEvent> eventHandler);
  public void setOnMouseExited(EventHandler<MouseEvent> eventHandler);
}
