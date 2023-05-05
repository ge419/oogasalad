package oogasalad.model.engine.prompt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IntegerPromptOptionTest {

  @Test
  void testPrompt() {
    IntegerPromptOption option = new IntegerPromptOption(42);
    assertEquals("42", option.prompt());
  }

  @Test
  void testGetValue() {
    IntegerPromptOption option = new IntegerPromptOption(42);
    assertEquals(42, option.getValue());
  }
}
