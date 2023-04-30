package oogasalad.model.engine.events;

import org.junit.Test;
import static org.junit.Assert.*;

public class StartGameEventTest {

  @Test
  public void testEventClass() {
    StartGameEvent event = new StartGameEvent();
    assertEquals(StartGameEvent.class, event.eventClass());
  }
}
