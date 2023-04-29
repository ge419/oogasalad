package oogasalad.view.gameplay;

import com.google.inject.assistedinject.Assisted;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javax.inject.Inject;
import oogasalad.controller.Effect;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.Prompter;

public class HumanPrompter implements Prompter {

  private final Consumer<Effect> doEffect;
  private final Gameview gameview;
  private Runnable dieCallback;
  private boolean callbackSet = false;

  @Inject
  public HumanPrompter(
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

    doEffect.accept(afterEffect -> dieCallback = () -> {
      callback.run();
      afterEffect.run();
    });
  }

  @Override
  public <T extends PromptOption> void selectSingleOption(
      String prompt,
      List<? extends T> options,
      Consumer<T> callback
  ) {
    Dialog<T> dialog = new Dialog<>();
    dialog.setTitle("Prompt");
    dialog.setContentText(prompt);
    dialog.setResultConverter(button ->
        options.stream()
            .filter(o -> o.prompt().equals(button.getText()))
            // Must exist because all buttons come from options
            .findAny().get());

    for (PromptOption option : options) {
      ButtonType button = new ButtonType(option.prompt());
      dialog.getDialogPane().getButtonTypes().add(button);
    }

    callback.accept(dialog.showAndWait().get());
  }
}