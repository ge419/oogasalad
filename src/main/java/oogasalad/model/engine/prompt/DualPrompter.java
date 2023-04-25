package oogasalad.model.engine.prompt;

import com.google.inject.assistedinject.Assisted;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import oogasalad.controller.Effect;
import oogasalad.view.gameplay.Gameview;

public class DualPrompter implements Prompter {
  private final Consumer<Effect> doEffect;
  private final Gameview gameview;

  @Inject
  public DualPrompter(
      @Assisted Consumer<Effect> doEffect,
      @Assisted Gameview gameview
  ) {
    this.doEffect = doEffect;
    this.gameview = gameview;
  }

  @Override
  public void rollDice(Runnable callback) {
    doEffect.accept(afterEffect -> {
      gameview.onDieClicked(
          () -> {
            System.out.println("running prompter");
            callback.run();
            afterEffect.run();
          });
    });
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