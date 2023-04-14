package oogasalad.model.engine;

/**
 * Represents a game-specific event.
 *
 * @author Dominic Martinez
 */
public interface Event<T extends Event<T>> {

  Class<T> eventClass();

  default String type() {
    return eventClass().getName();
  }
}