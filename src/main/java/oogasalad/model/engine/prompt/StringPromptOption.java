package oogasalad.model.engine.prompt;

public class StringPromptOption implements PromptOption {

  private final String value;

  public StringPromptOption(String value) {
    this.value = value;
  }

  @Override
  public String prompt() {
    return value;
  }

  public String getValue() {
    return value;
  }
}
