package oogasalad.model.engine.actions;

import java.util.Random;
import javax.inject.Inject;
import oogasalad.controller.GameHolder;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.events.DieRolledEvent;
import oogasalad.model.engine.events.TileLandedEvent;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class RollDieAndMoveAction implements Action {

  private final GameHolder game;
  private final Random random;


@Inject
  public RollDieAndMoveAction(Random random, GameHolder game) {
    this.game = game;
    this.random = random;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().rollDice(() -> afterDieRolled(actionParams));
  }

  private void afterDieRolled(ActionParams actionParams) {
    int value = random.nextInt(1, 7); // simulate rolling the dice
    actionParams.emitter().emit(new DieRolledEvent(value));
    String tileId = game.getPlayer().getCurrentTile();
    Tile tile = game.getBoard().getById(tileId);

    for (int i = 0; i < value; i++) {
      String nextTileId = tile.getNextTileIds().get(0);
      tile = game.getBoard().getById(nextTileId);
    }

    game.getPlayer().moveToTile(tile.getId());
    actionParams.emitter().emit(new TileLandedEvent(tile));
  }
}
