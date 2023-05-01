package oogasalad.model.engine.actions.scores;

import java.util.ResourceBundle;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;

/**
 * Action for buying Tiles.
 * <p>
 *   prompts user to buy or pass through the Tile upon landing
 *   Added to the engine action queue by ex. {@link oogasalad.model.engine.rules.BuyTileRule}
 * </p>
 *
 * @Author Jay Yoon
 */
public class BuyAction implements Action {

  private final Runnable buyProp;
  private final ResourceBundle bundle;

  public BuyAction(Runnable buyProp, @EngineResourceBundle ResourceBundle bundle) {
    this.buyProp = buyProp;
    this.bundle = bundle;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().yesNoDialog(bundle.getString(getClass().getSimpleName()), this::maybeBuy);
  }

  private void maybeBuy(boolean shouldBuy) {
    if (shouldBuy) {
      buyProp.run();
    }
  }
}
