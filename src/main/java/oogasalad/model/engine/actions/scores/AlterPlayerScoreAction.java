package oogasalad.model.engine.actions.scores;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.StringPromptOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action for updating Player score.
 * <p>
 * Updates the player score residing in GameHolder Added to the engine action queue by ex.
 * {@link oogasalad.model.engine.rules.ScoreTileRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class AlterPlayerScoreAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(AlterPlayerScoreAction.class);
  private static final String VALIDATION_OPTION = "Prompt1";
  private final Player player;
  private final double deltaScore;
  private final ResourceBundle bundle;


  @Inject
  public AlterPlayerScoreAction(
      @Assisted Player player,
      @Assisted double deltaScore,
      @EngineResourceBundle ResourceBundle bundle
  ) {
    this.player = player;
    this.deltaScore = deltaScore;
    this.bundle = bundle;
  }

  /**
   * executed action: updates player score with given score change value
   * <p>
   * constructs String prompter option to be validated by the user upon validation it will
   * increment/decrement player score by the appropriate amount
   * </p>
   *
   * @param actionParams incl. prompter
   */
  @Override
  public void runAction(ActionParams actionParams) {
    List<StringPromptOption> validation = new ArrayList<>();
    validation.add(
        new StringPromptOption(bundle.getString(getClass().getSimpleName() + VALIDATION_OPTION)));
    LOGGER.info("Updating player score for player: {}", player.getId());
    actionParams.prompter().selectSingleOption(
        String.format(bundle.getString(getClass().getSimpleName()), player.getId(), deltaScore),
        validation, this::updateScore);
  }

  private void updateScore(PromptOption option) {
    double newScore = player.getScore() + deltaScore;
    player.setScore(newScore);
    LOGGER.info("Player score updated to: {}", newScore);
  }
}
