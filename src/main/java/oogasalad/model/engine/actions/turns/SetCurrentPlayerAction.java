package oogasalad.model.engine.actions.turns;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.PlayerCreationEvent;

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

  /**
   * executed action: sets the current player of the Turn.
   * <p>
   *   retrieves the current player and updates the GameHolder accordingly
   *   specific implementation details are found {@link oogasalad.model.engine.rules.TurnRule}
   * </p>*
   * @param actionParams incl. emitter, prompter
   */
  @Override
  public void runAction(ActionParams actionParams) {
    game.setCurrentPlayer(nextPlayer);
  }
}
