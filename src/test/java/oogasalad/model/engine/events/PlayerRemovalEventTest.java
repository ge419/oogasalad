package oogasalad.model.engine.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class PlayerRemovalEventTest {

  @Test
  public void testEventClass() {
    PlayerRemovalEvent event = new PlayerRemovalEvent();
    assertEquals(PlayerRemovalEvent.class, event.eventClass());
  }
}
