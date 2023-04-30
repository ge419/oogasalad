package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.prompt.PromptOption;
import oogasalad.model.engine.prompt.StringPromptOption;

public class CheckWinStateAction implements Action {

  private final GameHolder gameHolder;
  private final int lastNStanding;

  @Inject
  public CheckWinStateAction(
      GameHolder holder,
      @Assisted int lastN
  ) {
    this.gameHolder = holder;
    this.lastNStanding = lastN;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    List<StringPromptOption> validation = new ArrayList<>();
    validation.add(new StringPromptOption("End Game"));
    if (gameHolder.getPlayers().getList().size()==lastNStanding) {
      actionParams.prompter().selectSingleOption(String.format("Congratulations on Being the Last %d Player!", lastNStanding), validation, this::gameEnd);
    }
  }

  private void gameEnd(PromptOption option) {
    System.out.println(option.toString());
  }
}
