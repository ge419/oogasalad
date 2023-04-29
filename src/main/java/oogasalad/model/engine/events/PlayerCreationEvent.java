package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;
public record PlayerCreationEvent() implements Event<PlayerCreationEvent> {

  @Override
  public Class<PlayerCreationEvent> eventClass() {
    return PlayerCreationEvent.class;
  }
}
