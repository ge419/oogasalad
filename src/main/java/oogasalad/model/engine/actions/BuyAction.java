package oogasalad.model.engine.actions;

import java.util.ResourceBundle;
import oogasalad.model.engine.EngineResourceBundle;

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
