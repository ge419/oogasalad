package oogasalad.model.engine.prompt;

/**
 * Defines an object that can be selected in a user prompt.
 *
 * @author Dominic Martinez
 */
public interface PromptOption {

  /**
   * Returns prompt to display to user.
   */
  String prompt();
}

