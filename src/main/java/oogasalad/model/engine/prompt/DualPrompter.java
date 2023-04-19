package oogasalad.model.engine.prompt;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class DualPrompter implements Prompter {

  @Override
  public void rollDice(Runnable callback) {
    effects.add((Runnable afterPresent) ->
        die.setCallback(() -> {
          callback.run();
          afterPresent.run();
        }));
  }

  @Override
  public void yesNoDialog(Consumer<Boolean> callback) {
    ButtonType yes = new ButtonType("Yes", ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonData.NO);
    Alert alert = new Alert(AlertType.CONFIRMATION,
        "Would you like to buy the property?",
        yes,
        no);

    alert.setTitle("Buy property?");

    effects.add((Runnable afterPresent) -> {
      Optional<ButtonType> result = alert.showAndWait();
      boolean answer = result.orElse(no) == yes;
      callback.accept(answer);
      afterPresent.run();
    });
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