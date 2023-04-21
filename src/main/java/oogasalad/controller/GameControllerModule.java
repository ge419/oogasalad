package oogasalad.controller;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.model.engine.EngineModule;

public class GameControllerModule extends AbstractModule {

  private final GameHolder gameHolderInstance;

  public GameControllerModule(GameHolder gameHolderInstance) {
    this.gameHolderInstance = gameHolderInstance;
  }

  @Override
  protected void configure() {
    install(new EngineModule());
    install(new FactoryModuleBuilder().build(PrompterFactory.class));
    bind(GameHolder.class).toInstance(gameHolderInstance);
  }
}