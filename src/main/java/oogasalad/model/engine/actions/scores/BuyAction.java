package oogasalad.model.engine.actions.scores;

import java.util.ResourceBundle;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;

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
