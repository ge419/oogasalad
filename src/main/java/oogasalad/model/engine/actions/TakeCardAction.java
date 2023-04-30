package oogasalad.model.engine.actions;

import javax.inject.Inject;
import oogasalad.model.constructable.GameHolder;

public class TakeCardAction implements Action {
  private final GameHolder gameHolder;

  @Inject
  public TakeCardAction(
      GameHolder holder
  ){
    this.gameHolder = holder;
  }

  @Override
  public void runAction(ActionParams actionParams) {

  }
}
