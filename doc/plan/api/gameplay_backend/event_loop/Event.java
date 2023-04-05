/**
 * A specific game event.
 * Events consist of a type and specific {@link Metadata}.
 */
interface Event {
  EventType type();
  Metadata metadata();
  Context context();
}