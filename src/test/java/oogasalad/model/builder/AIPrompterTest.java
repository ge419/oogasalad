package oogasalad.model.builder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import oogasalad.model.engine.prompt.AIPrompter;
import oogasalad.model.engine.prompt.PromptOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class AIPrompterTest {

  private AIPrompter aiPrompter;

  @Mock
  private PromptOption mockOption;

  @BeforeEach
  void setUp() {
    aiPrompter = new AIPrompter();
  }

  @Test
  void testRollDice() {
    boolean[] callbackCalled = {false};
    Runnable callback = () -> callbackCalled[0] = true;
    aiPrompter.rollDice(callback);
    assertTrue(callbackCalled[0]);
  }

  @Test
  void testSelectSingleOption() {
    // Given
    List<PromptOption> options = Arrays.asList(
        new PromptOption() {
          @Override
          public String prompt() {
            return "Buy";
          }
        },
        new PromptOption() {
          @Override
          public String prompt() {
            return "Sell";
          }
        },
        mockOption);
    boolean[] callbackCalled = {false};
    PromptOption[] selectedOption = {null};
    Consumer<PromptOption> callback = option -> {
      callbackCalled[0] = true;
      selectedOption[0] = option;
    };

    // When
    aiPrompter.selectSingleOption("Select an option", options, callback);

    // Then
    assertTrue(callbackCalled[0]);
    assertTrue(options.contains(selectedOption[0]));
  }
}