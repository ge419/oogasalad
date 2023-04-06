package oogasalad.model.engine.event_loop;

/**
 * Both registers handlers and triggers them.
 */
public interface EventHandlerManager extends EventRegistrar {

  /**
   * Trigger an event on all relevant {@link EventHandler}s.
   */
  void triggerEvent(EventHandlerParams params);
}
