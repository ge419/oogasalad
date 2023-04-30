package oogasalad.model.engine.prompt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
