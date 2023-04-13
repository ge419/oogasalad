package oogasalad.model.engine;

/**
 * Both registers handlers and triggers them.
 *
 * @author Dominic Martinez
 */
public interface EventHandlerManager extends EventRegistrar {

  /**
   * Trigger an event on all relevant {@link EventHandler}s.
   */
  void triggerEvent(EventHandlerParams<?> params);
}
