package oogasalad.model.engine.prompt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringPromptOptionTest {

  @Test
  public void testPrompt() {
    String value = "Hello, world!";
    StringPromptOption option = new StringPromptOption(value);
    assertEquals(value, option.prompt());
  }

  @Test
  public void testGetValue() {
    String value = "Hello, world!";
    StringPromptOption option = new StringPromptOption(value);
    assertEquals(value, option.getValue());
  }

}
