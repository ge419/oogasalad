package oogasalad.model.engine.prompt;

import com.google.inject.assistedinject.Assisted;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import oogasalad.controller.Effect;

public class DualPrompter implements Prompter {
  private final Consumer<Effect> doEffect;

  @Inject
  public DualPrompter(@Assisted Consumer<Effect> doEffect) {
    this.doEffect = doEffect;
  }

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