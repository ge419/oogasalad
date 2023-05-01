package oogasalad.model.engine.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameEndEventTest {

  @Test
  public void testEventClass() {
    GameEndEvent event = new GameEndEvent();
    assertEquals(GameEndEvent.class, event.eventClass());
  }

}
