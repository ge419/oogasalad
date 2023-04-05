/**
 * The primary abstraction for game behavior. Rules are triggered by events,
 * and can add actions to an {@link ActionQueue} to affect the game state.
 */
interface Rule {

  /**
   * Registers all the event listeners for this rule.
   *
   * @param emitter provides event registration methods
   */
  void registerEventHandlers(EventRegistrar emitter);
}