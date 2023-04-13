package oogasalad.model.engine;

/**
 * Emits events to the current event loop.
 *
 * @author Dominic Martinez
 */
@FunctionalInterface
public interface EventEmitter {

  /**
   * Synchronously emits an event.
   */
  void emit(Event<?> event);
}