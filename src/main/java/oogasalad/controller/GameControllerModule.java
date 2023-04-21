package oogasalad.controller;

import com.google.inject.AbstractModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.prompt.DualPrompter;
import oogasalad.model.engine.prompt.Prompter;

public class GameControllerModule extends AbstractModule {
  private GameHolder gameHolderInstance;

  public GameControllerModule(GameHolder gameHolderInstance) {
    this.gameHolderInstance = gameHolderInstance;
  }
  @Override
  protected void configure() {
    install(new EngineModule());
    // TODO don't take prompter through constructor
    bind(Prompter.class).to(DualPrompter.class);
    bind(GameHolder.class).toInstance(gameHolderInstance);
  }
}
