package oogasalad.model.engine;

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

