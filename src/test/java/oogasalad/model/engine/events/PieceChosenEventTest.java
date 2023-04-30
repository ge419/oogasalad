package oogasalad.model.engine.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PieceChosenEventTest {

  @Test
  public void testEventClass() {
    PieceChosenEvent event = new PieceChosenEvent(5);
    assertEquals(PieceChosenEvent.class, event.eventClass());
  }

}
