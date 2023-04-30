package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.events.GameEndEvent;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.StringPromptOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckTileWinAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(CheckTileWinAction.class);
  private final List<String> winningTileIds;
  private final String landedTileId;
  private final GameHolder gameHolder;
  private final ResourceBundle bundle;
  private static final String OPTION_1 = "Prompt1";

  @Inject
  public CheckTileWinAction(
      @Assisted String landedTileId,
      @Assisted List<String> winningTileIds,
      GameHolder holder,
      @EngineResourceBundle ResourceBundle bundle
  ) {
    this.landedTileId = landedTileId;
    this.winningTileIds = winningTileIds;
    this.gameHolder = holder;
    this.bundle = bundle;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    LOGGER.info("Checking if Current Game Player Status Satisfies Tile Winning Condition");
    if (winningTileIds.contains(landedTileId)) {
      LOGGER.info("Satisfied Condition, Ending Game");
      List<StringPromptOption> validation = new ArrayList<>();
      validation.add(new StringPromptOption(bundle.getString(getClass().getSimpleName()+OPTION_1)));
      actionParams.prompter().selectSingleOption(String.format(bundle.getString(getClass().getSimpleName())), validation, this::notifyEnd);
      actionParams.emitter().emit(new GameEndEvent());
    }
  }

  private void notifyEnd(PromptOption option) {
    gameHolder.notifyGameEnd();
  }
}
