package oogasalad.model.engine;

import oogasalad.model.engine.events.AttributeEvent;

/**
 * Provides registration for game events.
 *
 * @author Dominic Martinez
 */
public interface EventRegistrar {

  /**
   * Calls the provided handler when the given {@link Event} is triggered.
   */
  <T extends Event<T>> void registerHandler(Class<T> type, EventHandler<T> handler);

  void registerHandler(String type, EventHandler<AttributeEvent> handler);
}