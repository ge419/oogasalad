package oogasalad.model.engine;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import oogasalad.model.engine.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements a <em>stable</em> priority queue for actions.
 *
 * @author Dominic Martinez
 */
public class SimpleActionQueue implements ActionQueue {

  private static final Logger log = LogManager.getLogger(SimpleActionQueue.class);
  private final PriorityQueue<PriorityAction> queue;
  private long counter;

  public SimpleActionQueue() {
    queue = new PriorityQueue<>();
    counter = 0;
  }

  @Override
  public void add(int priority, Action action) {
    log.trace("action {} added to queue with priority {}", action, priority);
    // We include a counter in the comparison because PriorityQueue is not stable
    queue.add(new PriorityAction(priority, counter++, action));
  }

  @Override
  public Optional<Action> poll() {
    Optional<Action> optAction = Optional.ofNullable(queue.poll()).map(PriorityAction::action);

    if (optAction.isPresent()) {
      log.trace("polled {}", optAction.get());
    } else {
      log.debug("no action in queue");
    }

    return optAction;
  }

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  private record PriorityAction(int priority, long counter, Action action)
      implements Comparable<PriorityAction> {

    @Override
    public int compareTo(PriorityAction o) {
      Comparator<PriorityAction> comp =
          Comparator.comparingInt(PriorityAction::priority)
              .thenComparingLong(PriorityAction::counter);
      return comp.compare(this, o);
    }
  }
}
