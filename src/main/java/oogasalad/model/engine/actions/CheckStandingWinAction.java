package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.awt.Component;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.SwingUtilities;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.events.GameEndEvent;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.StringPromptOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckStandingWinAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(CheckStandingWinAction.class);
  private final GameHolder gameHolder;
  private final int lastNStanding;
  private final ResourceBundle bundle;
  private static final String OPTION_1 = "Prompt1";

  @Inject
  public CheckStandingWinAction(
      GameHolder holder,
      @Assisted int lastN,
      @EngineResourceBundle ResourceBundle bundle
  ) {
    this.gameHolder = holder;
    this.lastNStanding = lastN;
    this.bundle = bundle;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<StringPromptOption> validation = new ArrayList<>();
    validation.add(new StringPromptOption(bundle.getString(getClass().getSimpleName()+OPTION_1)));
    LOGGER.info("Checking if Current Game Players Status Satisfies Standing Winning Condition");
    if (gameHolder.getPlayers().getList().size()==lastNStanding) {
      LOGGER.info("Satisfied Condition, Ending Game");
      actionParams.prompter().selectSingleOption(String.format(bundle.getString(getClass().getSimpleName()), lastNStanding), validation, this::notifyEnd);
      actionParams.emitter().emit(new GameEndEvent());
    }
  }

  private void notifyEnd(PromptOption option) {
    gameHolder.notifyGameEnd();
  }
}
