package oogasalad.model.engine.prompt;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class AIPrompter implements Prompter {

  private final Random random;

  public AIPrompter() {
    this.random = new Random();
  }

  @Override
  public void rollDice(Runnable callback) {
    callback.run();
  }

  @Override
  public <T extends PromptOption> void selectSingleOption(
      String prompt,
      List<? extends T> options,
      Consumer<T> callback) {
    callback.accept(options.get(random.nextInt(options.size())));
  }
}
