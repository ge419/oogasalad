package oogasalad.model.engine.prompt;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

public class DualPrompter implements Prompter {
  //private Queue<UiEffect> effects;
  //private Die die;

  @Override
  public void rollDice(Runnable callback) {
  }

  @Override
  public void yesNoDialog(Consumer<Boolean> callback) {
  }

  @Override
  public <T extends PromptOption> void selectSingleOption(List<? extends T> options,
      Consumer<T> callback) {

  }

  @Override
  public <T extends PromptOption> void selectMultipleOptions(List<? extends T> options,
      Consumer<List<? extends T>> callback) {

  }
}