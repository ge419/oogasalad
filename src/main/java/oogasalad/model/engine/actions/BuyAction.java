package oogasalad.model.engine.actions;

import oogasalad.view.tiles.Tile;

public class BuyAction implements Action {

  private final Tile tile;

  public BuyAction(Tile tile) {
    this.tile = tile;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().yesNoDialog(this::maybeBuy);
  }

  private void maybeBuy(Boolean shouldBuy) {
    if (shouldBuy) {
      tile.setOwned(true);
    }
  }
}
