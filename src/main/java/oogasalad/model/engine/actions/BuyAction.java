package oogasalad.model.engine.actions;

public class BuyAction implements Action {

  private final Runnable buyProp;

  public BuyAction(Runnable buyProp) {
    this.buyProp = buyProp;
  }

  @Override
  public void runAction(ActionParams actionParams) {
    actionParams.prompter().yesNoDialog("Buy property?", this::maybeBuy);
  }

  private void maybeBuy(boolean shouldBuy) {
    if (shouldBuy) {
      buyProp.run();
    }
  }
}
