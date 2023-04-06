package oogasalad.model.engine.event_loop;

/**
 * Emits events to the current event loop.
 */
@FunctionalInterface
public interface EventEmitter {

  /**
   * Synchronously emits an event.
   */
  void emit(Event event);
}