package oogasalad.model.engine.actions.wins;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.GameEndEvent;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.StringPromptOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action for checking winning conditions and ending the Game if satisfied.
 * <p>
 * notifies the {@link oogasalad.controller.GameController} and
 * {@link oogasalad.view.gameplay.Gameview} to trigger appropriate actions Added to the engine
 * action queue by ex. {@link oogasalad.model.engine.rules.RemovePlayerRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class CheckWinAndEndAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(CheckWinAndEndAction.class);
  private static final String VALIDATION_OPTION = "Prompt1";
  private final WinningConditionStrategy winningCondition;
  private final ResourceBundle bundle;
  private final GameHolder gameHolder;

  @Inject
  public CheckWinAndEndAction(
      GameHolder holder,
      @Assisted WinningConditionStrategy winningCondition,
      @EngineResourceBundle ResourceBundle bundle
  ) {
    this.gameHolder = holder;
    this.winningCondition = winningCondition;
    this.bundle = bundle;
  }

  /**
   * executed action: checks winning condition and ends game if satisfied*
   * <p>
   * constructs mandatory String prompter selection option for user to validate notifies
   * {@link oogasalad.controller.GameController} and {@link oogasalad.view.gameplay.Gameview} to
   * trigger appropriate actions including automatically exiting current window
   * </p>
   * <p>
   * emits {@link GameEndEvent} that triggers end of game
   *
   * @param actionParams incl. emitter, prompter
   */
  @Override
  public void runAction(ActionParams actionParams) {
    LOGGER.info("Checking if Current Game Player Status Satisfies Winning Condition");
    if (winningCondition.isSatisfied()) {
      LOGGER.info("Satisfied Condition, Ending Game");
      List<StringPromptOption> validation = new ArrayList<>();
      validation.add(
          new StringPromptOption(bundle.getString(getClass().getSimpleName() + VALIDATION_OPTION)));
      actionParams.prompter()
          .selectSingleOption(String.format(bundle.getString(getClass().getSimpleName())),
              validation, this::notifyEnd);
      actionParams.emitter().emit(new GameEndEvent());
    }
  }

  private void notifyEnd(PromptOption option) {
    gameHolder.notifyGameEnd();
  }
}
