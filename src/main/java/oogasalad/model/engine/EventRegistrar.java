package oogasalad.model.engine;

import oogasalad.model.engine.events.EventType;

/**
 * Provides registration for game events.
 *
 * @author Dominic Martinez
 */
@FunctionalInterface
public interface EventRegistrar {

  /**
   * Calls the provided handler when the given {@link EventType} is triggered.
   */
  void registerHandler(EventType type, EventHandler handler);
}