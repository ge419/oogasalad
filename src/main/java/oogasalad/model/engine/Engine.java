package oogasalad.model.engine;

import java.util.List;
import oogasalad.model.engine.event_loop.EventHandler;
import oogasalad.model.engine.event_types.EventType;
import oogasalad.model.engine.event_loop.MissingActionsException;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.engine.event_types.EngineEvent;

/**
 * Implements the core engine for running a game.
 */
public interface Engine {

  /**
   * Runs a game via a list of rules. The following special {@link EventType}s are used:
   * <ul>
   *   <li>
   *     {@link EngineEvent#START_GAME}:
   *     This event is triggered automatically after rules have registered their listeners to
   *     bootstrap the system. Emitting this event during a currently running game initiates a
   *     subgame with rules as defined in the event's attributes.
   *   </li>
   *   <li>
   *     {@link EngineEvent#END_GAME}:
   *     Emitting this event immediately ends the game.
   *   </li>
   * </ul>
   *
   * @param rules list of rules for the game
   * @throws MissingActionsException if no actions are present in the queue after all
   * {@link EventHandler}s are triggered.
   */
  void run(List<Rule> rules);
}
