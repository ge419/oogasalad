/**
 * The engine implements the core {@link oogasalad.model.engine.rules.Rule} ->
 * {@link oogasalad.model.engine.actions.Action} -> {@link oogasalad.model.engine.event_loop.Event}
 * game loop. First, rules define handlers for available
 * {@link oogasalad.model.engine.event_types.EventType}s. Rules do not directly affect the game.
 * Instead, they add actions to a {@link oogasalad.model.engine.event_loop.ActionQueue}. Actions in
 * the queue are executed sequentially. Actions can modify the game state and emit events, which in
 * turn trigger rules, and so on until the
 * {@link oogasalad.model.engine.event_types.EngineEvent#END_GAME} event is emitted.
 *
 * @author Dominic Martinez
 * @see oogasalad.model.engine.Engine
 */

package oogasalad.model.engine;
