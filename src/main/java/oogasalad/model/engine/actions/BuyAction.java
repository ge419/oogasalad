package oogasalad.model.engine.actions;

import oogasalad.view.tiles.ViewTile;

public class BuyAction implements Action {

  private final ViewTile tile;

  public BuyAction(ViewTile tile) {
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
