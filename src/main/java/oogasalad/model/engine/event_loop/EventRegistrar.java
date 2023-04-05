package oogasalad.model.engine.event_loop;

import oogasalad.model.engine.event_types.EventType;

/**
 * Provides registration for game events.
 */
@FunctionalInterface
public interface EventRegistrar {

  /**
   * Calls the provided handler when the given {@link EventType} is triggered.
   */
  void registerHandler(EventType type, EventHandler handler);
}