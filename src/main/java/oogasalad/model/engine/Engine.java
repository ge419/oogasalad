package oogasalad.model.engine;

import java.util.List;
import oogasalad.model.engine.events.EngineEvent;
import oogasalad.model.engine.events.EventType;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.rules.Rule;

/**
 * Implements the core engine for running a game.
 *
 * <p>A game consists of a list of rules, which listens to events that are emitted by actions they
 * create. The engine supports subgames, which allow a different set of rules to be run temporarily.
 * Once the subgame has exhausted all actions in its queue, it returns to the parent game with the
 * original ruleset. Parent rules hear events emitted by the subgame, but actions that they register
 * do not run until the subgame is complete.
 *
 * @author Dominic Martinez
 */
public interface Engine {

  /**
   * Sets the current list of rules, clearing all currently registered event handlers.
   * {@link Rule#registerEventHandlers(EventRegistrar)} is called on each rule in order. In addition
   * to events emitted by actions, the following special {@link EventType}s are emitted:
   * <ul>
   *   <li>
   *     {@link EngineEvent#START_GAME}:
   *     This event is triggered automatically the first time {@link Engine#runNextAction(Prompter)}
   *     is run. Emitting this event during a currently running game initiates a subgame with rules
   *     as defined in the event's attributes.
   *   </li>
   * </ul>
   *
   * @param rules list of rules for the game
   */
  void setRules(List<? extends Rule> rules);

  /**
   * Runs the next available action across this game and all subgames.
   *
   * @param prompter may be called to present a user prompt
   * @throws MissingActionsException if no actions are pending.
   */
  void runNextAction(Prompter prompter);
}
