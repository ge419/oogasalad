package oogasalad.model.engine.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StartTurnEventTest {

  @Test
  public void testEventClass() {
    StartTurnEvent event = new StartTurnEvent();
    assertEquals(StartTurnEvent.class, event.eventClass());
  }
}
