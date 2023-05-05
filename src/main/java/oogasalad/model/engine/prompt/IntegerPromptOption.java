package oogasalad.model.engine.prompt;

public class IntegerPromptOption implements PromptOption {

  private final int value;

  public IntegerPromptOption(int value) {
    this.value = value;
  }

  @Override
  public String prompt() {
    return Integer.toString(value);
  }

  public int getValue() {
    return value;
  }
}
