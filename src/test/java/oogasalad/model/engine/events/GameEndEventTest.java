package oogasalad.model.engine.events;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameEndEventTest {

  @Test
  public void testEventClass() {
    GameEndEvent event = new GameEndEvent();
    assertEquals(GameEndEvent.class, event.eventClass());
  }

}
