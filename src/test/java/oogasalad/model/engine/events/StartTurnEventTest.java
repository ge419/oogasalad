package oogasalad.model.engine.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StartTurnEventTest {

  @Test
  public void testEventClass() {
    StartTurnEvent event = new StartTurnEvent();
    assertEquals(StartTurnEvent.class, event.eventClass());
  }
}
