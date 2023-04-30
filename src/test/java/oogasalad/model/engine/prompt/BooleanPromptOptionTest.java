package oogasalad.model.engine.prompt;

import org.junit.Test;
import static org.junit.Assert.*;

public class BooleanPromptOptionTest {

  @Test
  public void testPromptReturnsYesWhenTrue() {
    BooleanPromptOption option = new BooleanPromptOption(true);
    assertEquals("Yes", option.prompt());
  }

  @Test
  public void testPromptReturnsNoWhenFalse() {
    BooleanPromptOption option = new BooleanPromptOption(false);
    assertEquals("No", option.prompt());
  }

  @Test
  public void testIsTrueReturnsTrueWhenConstructedWithTrue() {
    BooleanPromptOption option = new BooleanPromptOption(true);
    assertTrue(option.isTrue());
  }

  @Test
  public void testIsTrueReturnsFalseWhenConstructedWithFalse() {
    BooleanPromptOption option = new BooleanPromptOption(false);
    assertFalse(option.isTrue());
  }
}
