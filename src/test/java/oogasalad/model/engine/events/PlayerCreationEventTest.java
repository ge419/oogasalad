package oogasalad.model.engine.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerCreationEventTest {

  @Test
  public void testEventClass() {
    PlayerCreationEvent event = new PlayerCreationEvent();
    assertEquals(PlayerCreationEvent.class, event.eventClass());
  }

}
