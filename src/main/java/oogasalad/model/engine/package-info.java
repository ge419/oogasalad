/**
 * The engine implements the core {@link oogasalad.model.engine.rules.Rule} ->
 * {@link oogasalad.model.engine.actions.Action} -> {@link oogasalad.model.engine.Event} game loop.
 * First, rules define handlers for available {@link oogasalad.model.engine.Event}s. Rules do not
 * directly affect the game. Instead, they add actions to a
 * {@link oogasalad.model.engine.ActionQueue}. Actions in the queue are executed sequentially.
 * Actions can modify the game state and emit events, which in turn trigger rules, and so on. After
 * each action, the {@link oogasalad.model.engine.Engine} delegates back to the frontend to perform
 * animations and receive user input before continuing.
 *
 * @author Dominic Martinez
 * @see oogasalad.model.engine.Engine
 */

package oogasalad.model.engine;
