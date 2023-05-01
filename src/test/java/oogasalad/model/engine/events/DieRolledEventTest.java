package oogasalad.model.engine.events;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DieRolledEventTest {

  @Test
  public void testGetNumbersRolled() {
    int[] numbers = new int[]{1, 2, 3, 4, 5};
    DieRolledEvent event = new DieRolledEvent(numbers);
    assertArrayEquals(numbers, event.getNumbersRolled());
  }

  @Test
  public void testEventClass() {
    DieRolledEvent event = new DieRolledEvent(new int[]{1, 2, 3});
    assertEquals(DieRolledEvent.class, event.eventClass());
  }

  @Test
  public void testType() {
    DieRolledEvent event = new DieRolledEvent(new int[]{1, 2, 3});
    assertEquals(DieRolledEvent.class.getName(), event.type());
  }
}
