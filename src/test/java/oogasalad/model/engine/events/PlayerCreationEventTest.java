package oogasalad.model.engine.events;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerCreationEventTest {

  @Test
  public void testEventClass() {
    PlayerCreationEvent event = new PlayerCreationEvent();
    assertEquals(PlayerCreationEvent.class, event.eventClass());
  }

}
