package oogasalad.model.engine.events;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AttributeEventTest {

  private static final String TEST_KEY = "testKey";

  @Test
  public void testEventClass() {
    AttributeEvent event = new AttributeEvent(TEST_KEY);
    assertEquals(AttributeEvent.class, event.eventClass());
  }

  @Test
  public void testType() {
    AttributeEvent event = new AttributeEvent(TEST_KEY);
    assertEquals(TEST_KEY, event.type());
  }
}
