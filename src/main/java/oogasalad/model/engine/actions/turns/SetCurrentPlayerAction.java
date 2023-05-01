package oogasalad.model.engine.actions.turns;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;

/**
 * Action for updating current Player upon the start of every new Turn.
 * <p>
 *   updates current player on GameHolder accordingly
 *   Added to the engine action queue by ex. {@link oogasalad.model.engine.rules.TurnRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class SetCurrentPlayerAction implements Action {

  private final GameHolder game;
  private final Player nextPlayer;

  @Inject
  public SetCurrentPlayerAction(GameHolder game, @Assisted Player nextPlayer) {
    this.game = game;
    this.nextPlayer = nextPlayer;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    game.setCurrentPlayer(nextPlayer);
  }
}
