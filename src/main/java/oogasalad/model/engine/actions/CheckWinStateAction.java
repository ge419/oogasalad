package oogasalad.model.engine.actions;

import com.google.inject.Inject;
import oogasalad.model.constructable.GameHolder;

public class CheckWinStateAction implements Action {

  private final GameHolder gameHolder;
  private final int lastNStanding = 1;

  @Inject
  public CheckWinStateAction(
      GameHolder holder
  ) {
    this.gameHolder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    if (gameHolder.getPlayers().get().getPlayers().size()==lastNStanding) {
      System.out.println("YOU WIN!!!!");
    }
  }
}
