package oogasalad.model.engine.prompt;

import java.util.List;
import java.util.function.Consumer;

/**
 * Defines an object that can prompt the user for actions, and call the specified callback once
 * complete.
 *
 * @author Dominic Martinez
 */
public interface Prompter {

  /**
   * Prompts the user to roll the appropriate dice. Note that the chooser is not responsible for
   * selecting a number. This method is just used to pause the game until the player is ready.
   * TODO: Support multiple types of dice
   */
  void rollDice(Runnable callback);

  void yesNoDialog(Consumer<Boolean> callback);

  /**
   * Prompts the user to select a single option.
   *
   * @param options  list of options to be presented to the user
   * @param callback consumes the selected option; should be given the exact same object as what was
   *                 in the option list
   * @param <T>      type of the options to present
   */
  <T extends PromptOption> void selectSingleOption(List<? extends T> options, Consumer<T> callback);

  /**
   * Prompts the user to select multiple options.
   * TODO: support selecting a minimum/maximum number of options
   *
   * @param options  list of options to be presented to the user
   * @param callback consumes the selected options; should be given the exact same objects as what
   *                 was in the option list
   * @param <T>      type of the options to present
   */
  <T extends PromptOption> void selectMultipleOptions(
      List<? extends T> options, Consumer<List<? extends T>> callback);

}
