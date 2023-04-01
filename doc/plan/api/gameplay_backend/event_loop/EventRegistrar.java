/**
 * Provides registration for game events.
 */
interface EventRegistrar {

  /**
   * Calls the provided handler when the given {@link EventType} is triggered.
   */
  void handleEvent(EventType type, EventHandler handler);
}