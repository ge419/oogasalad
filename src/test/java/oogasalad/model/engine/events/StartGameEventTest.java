package oogasalad.model.engine.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StartGameEventTest {

  @Test
  public void testEventClass() {
    StartGameEvent event = new StartGameEvent();
    assertEquals(StartGameEvent.class, event.eventClass());
  }
}
