package oogasalad.model.engine.events;

import oogasalad.model.engine.Engine;

/**
 * Enumerates built-in events recognized by the {@link Engine}.
 *
 * @author Dominic Martinez
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
