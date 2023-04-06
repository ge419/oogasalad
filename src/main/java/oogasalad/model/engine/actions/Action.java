package oogasalad.model.engine.actions;

/**
 * The primary abstraction for changing the game state.
 * An action is generally an atomic, unconditional operation.
 */
public interface Action {

  /**
   * Runs an action, possibly emitting events in the process.
   */
  void runAction(ActionParams actionParams);
}