package oogasalad.model.engine.prompt;

public class BooleanPromptOption implements PromptOption {

  private final boolean value;

  public BooleanPromptOption(
      boolean value
  ) {
    this.value = value;
  }

  @Override
  public String prompt() {
    return value ? "Yes" : "No";
  }

  public boolean isTrue() {
    return value;
  }
}
