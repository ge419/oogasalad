package oogasalad.model.engine.actions;

/**
 * The primary abstraction for changing the game state. An action is generally an atomic,
 * unconditional operation.
 *
 * @author Dominic Martinez
 */
public interface Action {

  /**
   * Runs an action, possibly emitting events in the process.
   */
  void runAction(ActionParams actionParams);
}