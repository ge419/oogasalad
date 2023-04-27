package oogasalad.model.engine.actions;

import com.google.inject.assistedinject.Assisted;
import javax.inject.Inject;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

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
