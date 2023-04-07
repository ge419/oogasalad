package oogasalad.model.engine.event_loop;

/**
 * Defines handlers called when an {@link Event} is triggered.
 */
@FunctionalInterface
public interface EventHandler {

  /**
   * Handles an event, possibly adding actions to the queue.
   */
  void handleEvent(EventHandlerParams params);
}