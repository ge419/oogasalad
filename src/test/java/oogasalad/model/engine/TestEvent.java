package oogasalad.model.engine;

import oogasalad.model.engine.event_types.EventType;

public enum TestEvent implements EventType {
  TEST_EVENT_1, TEST_EVENT_2, TEST_EVENT_3, TEST_EVENT_4;

  @Override
  public String type() {
    return this.toString();
  }
}
