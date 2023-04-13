package oogasalad.model.engine;

/**
 * Defines handlers called when an {@link Event} is triggered.
 *
 * @author Dominic Martinez
 */
@FunctionalInterface
public interface EventHandler<T extends Event<T>> {

  /**
   * Handles an event, possibly adding actions to the queue.
   */
  void handleEvent(EventHandlerParams<T> params);
}