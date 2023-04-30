package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.StringPromptOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlterPlayerScoreAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(AlterPlayerScoreAction.class);
  private final Player player;
  private final double deltaScore;

  @Inject
  public AlterPlayerScoreAction(
      @Assisted Player player,
      @Assisted double deltaScore
  ) {
    this.player = player;
    this.deltaScore = deltaScore;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<StringPromptOption> validation = new ArrayList<>();
    validation.add(new StringPromptOption("OK"));
    LOGGER.info("Update Player Score");
    actionParams.prompter().selectSingleOption(String.format("Player %s: %.2f Updated To Score", player.getId(), deltaScore), validation, this::updateScore);
  }

  private void updateScore(PromptOption option) {
    double score = player.getScore();
    double newScore = score + (deltaScore);
    player.setScore(newScore);
  }
}
