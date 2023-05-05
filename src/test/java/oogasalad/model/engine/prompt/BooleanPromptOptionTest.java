package oogasalad.model.engine.prompt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
