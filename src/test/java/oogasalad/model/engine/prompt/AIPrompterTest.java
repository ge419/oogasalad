package oogasalad.model.engine.prompt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AIPrompterTest {

  private AIPrompter prompter;

  @Before
  public void setUp() {
    prompter = new AIPrompter();
  }

  @Test
  public void testRollDice() {
    Runnable callback = mock(Runnable.class);
    prompter.rollDice(callback);
    verify(callback, times(1)).run();
  }

  @Test
  public void testSelectSingleOption() {
    List<PromptOption> options = new ArrayList<>();
    PromptOption option1 = mock(PromptOption.class);
    PromptOption option2 = mock(PromptOption.class);
    PromptOption option3 = mock(PromptOption.class);
    options.add(option1);
    options.add(option2);
    options.add(option3);
    Consumer<PromptOption> callback = mock(Consumer.class);
    prompter.selectSingleOption("Choose an option:", options, callback);
    verify(callback, times(1)).accept(any(PromptOption.class));
  }

}
