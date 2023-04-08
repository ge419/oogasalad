package oogasalad.model.engine.rules;

import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventRegistrar;

/**
 * The primary abstraction for game behavior. Rules are triggered by events, and can add actions to
 * an {@link ActionQueue} to affect the game state.
 *
 * @author Dominic Martinez
 */
public interface Rule {

  /**
   * Registers all the event listeners for this rule.
   *
   * @param registrar provides event registration methods
   */
  void registerEventHandlers(EventRegistrar registrar);
}