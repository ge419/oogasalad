package oogasalad.model.engine.events;

public enum TurnEvent implements EventType {
  START_TURN("start-turn"), DIE_ROLLED("die-roll");

  private final String type;

  TurnEvent(String type) {
    this.type = type;
  }

  @Override
  public String type() {
    return type;
  }
}
