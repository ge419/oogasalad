package oogasalad.model.engine.events;

public enum MonopolyEvent implements EventType {
  START_TURN("start-turn"), DIE_ROLLED("die-roll"), LANDED("landed");

  private final String type;

  MonopolyEvent(String type) {
    this.type = type;
  }

  @Override
  public String type() {
    return type;
  }
}
