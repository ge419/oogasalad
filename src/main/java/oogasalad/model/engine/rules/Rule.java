package oogasalad.model.engine.rules;

import oogasalad.model.engine.event_loop.ActionQueue;
import oogasalad.model.engine.event_loop.EventRegistrar;

/**
 * The primary abstraction for game behavior. Rules are triggered by events,
 * and can add actions to an {@link ActionQueue} to affect the game state.
 */
public interface Rule {

  /**
   * Registers all the event listeners for this rule.
   *
   * @param registrar provides event registration methods
   */
  void registerEventHandlers(EventRegistrar registrar);
}