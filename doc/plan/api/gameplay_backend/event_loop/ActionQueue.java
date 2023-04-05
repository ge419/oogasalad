/**
 * A priority queue of {@link Action}s.
 */
interface ActionQueue {

  /**
   * Adds an action to the queue.
   *
   * @param priority priority in ascending order; the smallest numerical priority is taken first
   * @param action action to add to the queue.
   */
  void add(int priority, Action action);

  /**
   * Returns an action first by ascending priority, and then by first-in-first-out.
   */
  Action pop();
}