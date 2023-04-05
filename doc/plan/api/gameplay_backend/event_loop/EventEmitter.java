/**
 * Emits events to the current event loop.
 */
interface EventEmitter {

  /**
   * Synchronously emits an event.
   */
  void emit(Event event);
}