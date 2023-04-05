package oogasalad.model.engine.event_loop;

import java.util.Optional;
import java.util.PriorityQueue;
import oogasalad.model.engine.actions.Action;

public class SimpleActionQueue implements ActionQueue {
  
  PriorityQueue<PriorityAction> queue;
  
  public SimpleActionQueue() {
    queue = new PriorityQueue<>();
  }

  @Override
  public void add(int priority, Action action) {
    queue.add(new PriorityAction(priority, action));
  }

  @Override
  public Optional<Action> poll() {
    return Optional.ofNullable(queue.poll()).map(PriorityAction::action);
  }
  
  private record PriorityAction(int priority, Action action) implements Comparable<PriorityAction> {

    @Override
    public int compareTo(PriorityAction o) {
      return Integer.compare(this.priority(), o.priority);
    }
  }
}
