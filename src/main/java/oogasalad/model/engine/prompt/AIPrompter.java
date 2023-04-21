package oogasalad.model.engine.prompt;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import javafx.scene.Node;

public class AIPrompter implements Prompter{

  @Override
  public void rollDice(Runnable callback) {
    callback.run();
  }

  @Override
  public void yesNoDialog(Consumer<Boolean> callback) {
    callback.accept(getRandomBoolean(90.0f));
  }

  private boolean getRandomBoolean(float probability) {
    double randomValue = Math.random()*100;  //0.0 to 99.9
    return randomValue <= probability;
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
