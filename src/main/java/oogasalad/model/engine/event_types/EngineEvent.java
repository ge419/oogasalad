package oogasalad.model.engine.event_types;

import oogasalad.model.engine.Engine;

/**
 * @see Engine
 */
public enum EngineEvent implements EventType {
  START_GAME("start-game");

  private final String type;

  EngineEvent(String type) {
    this.type = type;
  }

  @Override
  public String type() {
    return type;
  }
}
