/**
 * The primary abstraction for changing the game state.
 * An action is generally an atomic, unconditional operation.
 */
interface BiAction extends Consumer<EventEmitter, Chooser> { }