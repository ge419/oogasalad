package oogasalad.model.engine;

/**
 * Enum that represents the priority of the {@link oogasalad.model.engine.actions.Action} Actions
 * with high priority are executed before those with lower priorities
 *
 * @author Jay Yoon
 */
public enum Priority {
  MOST_HIGH(0),
  HIGH(3),
  MEDIUM(5),
  LOW(10);

  private final int value;

  Priority(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}

