package oogasalad.model.engine.prompt;

import com.google.inject.assistedinject.Assisted;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import oogasalad.controller.Effect;
import oogasalad.view.gameplay.DieClickedEvent;
import oogasalad.view.gameplay.Gameview;

public class DualPrompter implements Prompter {

  private final Consumer<Effect> doEffect;
  private final Gameview gameview;
  private Runnable dieCallback;
  private boolean callbackSet = false;

  @Inject
  public DualPrompter(
      @Assisted Consumer<Effect> doEffect,
      @Assisted Gameview gameview
  ) {
    this.doEffect = doEffect;
    this.gameview = gameview;
  }

  private void dieClicked() {
    if (dieCallback != null) {
      dieCallback.run();
    }
  }

  @Override
  public void rollDice(Runnable callback) {
    // TODO: This is horrible but necessary until prompters are constructed after the view is initialized
    if (!callbackSet) {
      gameview.addEventHandler(DieClickedEvent.DIE_CLICKED, e -> dieClicked());
      callbackSet = true;
    }

    // TODO: Infinite loop here
    doEffect.accept(afterEffect -> {
      dieCallback = () -> {
        callback.run();
        afterEffect.run();
      };
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