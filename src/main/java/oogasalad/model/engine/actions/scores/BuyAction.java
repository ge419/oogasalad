package oogasalad.model.engine.actions.scores;

import java.util.ResourceBundle;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.events.PlayerCreationEvent;

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

  /**
   * executed action: prompts the user to select whether to buy Tile*
   * <p>
   *   constructs the Boolean prompter option for the user to select whether to buy the landed tile
   *   if selected positive, runs the callback to process buying, including updating tile owner and player score
   * </p>*
   * @param actionParams incl. prompter
   */
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
