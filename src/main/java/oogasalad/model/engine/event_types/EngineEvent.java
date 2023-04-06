package oogasalad.model.engine.event_types;

import oogasalad.model.engine.Engine;

/**
 * @see Engine
 */
public enum EngineEvent implements EventType {
  START_GAME, END_GAME;

  @Override
  public String type() {
    return this.toString();
  }
}
