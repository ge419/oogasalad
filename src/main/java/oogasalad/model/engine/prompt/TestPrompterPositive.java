package oogasalad.model.engine.prompt;

import java.util.List;
import java.util.function.Consumer;

public class TestPrompterPositive implements Prompter {

  @Override
  public void rollDice(Runnable callback) {
    callback.run();
  }

  @Override
  public <T extends PromptOption> void selectSingleOption(String prompt, List<? extends T> options,
      Consumer<T> callback) {
    callback.accept(options.get(0));
  }
}
