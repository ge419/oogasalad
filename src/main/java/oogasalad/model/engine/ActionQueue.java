package oogasalad.model.engine;

import java.util.Optional;
import oogasalad.model.engine.actions.Action;

/**
 * A <em>stable</em> priority queue of {@link Action}s.
 *
 * @author Dominic Martinez
 */
public interface ActionQueue {

  /**
   * Adds an action to the queue.
   *
   * @param priority priority in ascending order; the smallest numerical priority is taken first
   * @param action   action to add to the queue.
   */
  void add(int priority, Action action);

  /**
   * Returns an action first by ascending priority, and then by first-in-first-out.
   *
   * @return an action, or {@link Optional#empty()} if no actions are present.
   */
  Optional<Action> poll();

  /**
   * Returns whether the queue is empty.
   *
   * @return true if the queue is empty, false otherwise
   */
  boolean isEmpty();
}