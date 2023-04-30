package oogasalad.model.engine.events;

import org.junit.Test;

import static org.junit.Assert.*;

public class DieRolledEventTest {

  @Test
  public void testGetNumbersRolled() {
    int[] numbers = new int[] {1, 2, 3, 4, 5};
    DieRolledEvent event = new DieRolledEvent(numbers);
    assertArrayEquals(numbers, event.getNumbersRolled());
  }

  @Test
  public void testEventClass() {
    DieRolledEvent event = new DieRolledEvent(new int[] {1, 2, 3});
    assertEquals(DieRolledEvent.class, event.eventClass());
  }

  @Test
  public void testType() {
    DieRolledEvent event = new DieRolledEvent(new int[] {1, 2, 3});
    assertEquals(DieRolledEvent.class.getName(), event.type());
  }
}
