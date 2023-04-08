package oogasalad.model.engine;

/**
 * Defines handlers called when an {@link Event} is triggered.
 *
 * @author Dominic Martinez
 */
@FunctionalInterface
public interface EventHandler {

  /**
   * Handles an event, possibly adding actions to the queue.
   */
  void handleEvent(EventHandlerParams params);
}