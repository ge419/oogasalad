package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;

public class AttributeEvent implements Event<AttributeEvent> {

  private final String key;

  // TODO allow attributes

  public AttributeEvent(String key) {
    this.key = key;
  }

  @Override
  public Class<AttributeEvent> eventClass() {
    return AttributeEvent.class;
  }

  @Override
  public String type() {
    return key;
  }
}
