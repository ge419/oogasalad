/**
 * Handler for game events. Registered by {@link Rule}s.
 */
interface EventHandler extends BiConsumer<Event, ActionQueue> { }