package oogasalad.model.engine.actions.wins;

import oogasalad.model.constructable.GameHolder;

public class StandingWinningStrategy implements WinningConditionStrategy {
  private GameHolder gameHolder;
  private int lastNStanding;

  public StandingWinningStrategy(GameHolder gameHolder, int lastNStanding) {
    this.gameHolder = gameHolder;
    this.lastNStanding = lastNStanding;
  }

  @Override
  public boolean isSatisfied() {
    return gameHolder.getPlayers().getList().size() == lastNStanding;
  }
}
