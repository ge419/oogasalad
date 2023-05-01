package oogasalad.model.engine.actions.moves;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.DieRolledEvent;

public class RollDieAction implements Action {

  private final int[] results;

  @Inject
  public RollDieAction(@Assisted int[] results) {
    this.results = results;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().rollDice(() -> afterDieRolled(actionParams));
  }

  private void afterDieRolled(ActionParams actionParams) {
    actionParams.emitter().emit(new DieRolledEvent(results));
  }
}
