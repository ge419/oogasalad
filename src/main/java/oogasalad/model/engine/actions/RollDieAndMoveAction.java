package oogasalad.model.engine.actions;

import java.util.Map;
import java.util.Random;
import javax.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.Event;
import oogasalad.model.engine.events.MonopolyEvent;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.Tiles;

public class RollDieAndMoveAction implements Action {

  private final Tiles tiles;
  private final PlayerPiece piece;
  private final Random random;

  @Inject
  public RollDieAndMoveAction(Random random, Tiles tiles, PlayerPiece piece) {
    this.random = random;
    this.tiles = tiles;
    this.piece = piece;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().rollDice(() -> afterDieRolled(actionParams));
  }

  private void afterDieRolled(ActionParams actionParams) {
    int value = random.nextInt(1, 7); // simulate rolling the dice
    actionParams.emitter().emit(new Event(
        MonopolyEvent.DIE_ROLLED, Map.of("value", new IntAttribute("value", value))));
    Tile tile = piece.getCurrentTile();

    for (int i = 0; i < value; i++) {
      String nextTileId = tile.getNextTileIds().get(0);
      tile = tiles.getTile(nextTileId).getTile();
    }

    piece.moveToTile(tile);
    actionParams.emitter().emit(new Event(
        MonopolyEvent.LANDED, Map.of("tile", new TileAttribute("tile", tile.getId()))
    ));
  }
}
