package oogasalad.model.engine.actions.wins;

import oogasalad.model.constructable.GameHolder;

/**
 * Concrete implementation of the {@link WinningConditionStrategy}
 *
 * Used by Actions to check whether player is the last N player left (ex. {@link CheckWinAndEndAction})
 * Able to be used by games whose goal is to survive throughout the game
 *
 * @Author Jay Yoon
 */
public class StandingWinningStrategy implements WinningConditionStrategy {
  private GameHolder gameHolder;
  private int lastNStanding;

  public StandingWinningStrategy(GameHolder gameHolder, int lastNStanding) {
    this.gameHolder = gameHolder;
    this.lastNStanding = lastNStanding;
  }

  /**
   * method that checks whether the current state of GameHolder satisfies the given condition
   * <p>
   *   checks if the number of players left in the game is equal to the number of winners of the game
   *   if equal, the game end condition is satisfied
   * </p>
   *
   * @return boolean that represents whether winning condition is satisfied
   */
  @Override
  public boolean isSatisfied() {
    return gameHolder.getPlayers().getList().size() == lastNStanding;
  }
}
