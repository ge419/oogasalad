package oogasalad.model.engine.actions;

import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.constructable.Tile;

public class BuyAction implements Action {

  public static final String OWNED_ATTRIBUTE = "owned";

  private final Tile tile;

  public BuyAction(Tile tile) {
    this.tile = tile;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().yesNoDialog(this::maybeBuy);
  }

  private void maybeBuy(boolean shouldBuy) {
    if (shouldBuy) {
      BooleanAttribute ownedAttribute = BooleanAttribute.from(tile.getAttribute(OWNED_ATTRIBUTE));
      ownedAttribute.setValue(true);
    }
  }
}
