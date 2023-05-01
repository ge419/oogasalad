package oogasalad.model.engine.actions.removal;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.PlayerCreationEvent;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action for removing players and updating GameHolder Players.
 * <p>
 * Players whose scores are below given lower bound are removed from the GameHolder. Added to the
 * engine action queue by {@link oogasalad.model.engine.rules.RemovePlayerRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class CheckAndRemovePlayerAction implements Action {

  private static final Logger LOGGER = LogManager.getLogger(CheckAndRemovePlayerAction.class);
  private final GameHolder gameholder;
  private PlayerRemovalStrategy removalStrategy;
  private TileResetStrategy tileResetStrategy;
  private final int scoreMinBound;

  @Inject
  public CheckAndRemovePlayerAction(
      GameHolder holder,
      @Assisted int scoreMinBound,
      @Assisted PlayerRemovalStrategy removalStrategy,
      @Assisted TileResetStrategy tileResetStrategy
  ) {
    this.gameholder = holder;
    this.scoreMinBound = scoreMinBound;
    this.removalStrategy = removalStrategy;
    this.tileResetStrategy = tileResetStrategy;
  }

  /**
   * executed action: removal of players from the game, updating GameHolder accordingly.*
   * <p>
   * uses {@link PlayerRemovalStrategy} to remove players from the game with given conditions uses
   * {@link TileResetStrategy} to set the tiles of the list of removed players to unowned
   * </p>
   * <p>
   * emits {@link PlayerRemovalEvent} that triggers other rules ex.
   * {@link oogasalad.model.engine.actions.wins.CheckWinAndEndAction}
   *
   * @param actionParams
   */
  @Override
  public void runAction(ActionParams actionParams) {
    List<Player> playersToRemove = removalStrategy.removePlayers(gameholder, scoreMinBound);
    actionParams.emitter().emit(new PlayerRemovalEvent());
    tileResetStrategy.resetTilesOwned(gameholder, playersToRemove);
  }

}
