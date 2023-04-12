package oogasalad.model.engine.actions;

import java.util.Map;
import javax.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.TileAttribute;
import oogasalad.model.engine.Event;
import oogasalad.model.engine.events.MonopolyEvent;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.tiles.ViewTile;
import oogasalad.view.tiles.Tiles;

public class RollDieAndMoveAction implements Action {

  private final Tiles tiles;
  private final PlayerPiece piece;

  @Inject
  public RollDieAndMoveAction(Tiles tiles, PlayerPiece piece) {
    this.tiles = tiles;
    this.piece = piece;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().rollDice(() -> afterDieRolled(actionParams));
  }

  private void afterDieRolled(ActionParams actionParams) {
    System.out.println("Die rolled");
    int value = (int) (Math.random() * 6) + 1; // simulate rolling the dice
    actionParams.emitter().emit(new Event(
        MonopolyEvent.DIE_ROLLED, Map.of("value", new IntAttribute("value", value))));
    ViewTile tile = piece.getCurrentTile();

    for (int i = 0; i < value; i++) {
      int nextTileId = tile.getNext()[0];
      tile = tiles.getTile(nextTileId);
    }

    piece.moveToTile(tile);
    actionParams.emitter().emit(new Event(
        MonopolyEvent.LANDED, Map.of("tile", new TileAttribute("tile", tile.getTileId()))
    ));
  }
}
