package oogasalad.controller;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EngineModule;
import oogasalad.view.ViewFactory;

public class GameControllerModule extends AbstractModule {

  private final GameHolder gameHolderInstance;

  public GameControllerModule(GameHolder gameHolderInstance) {
    this.gameHolderInstance = gameHolderInstance;
  }

  @Override
  protected void configure() {
    install(new EngineModule());
    install(new FactoryModuleBuilder().build(PrompterFactory.class));
    install(new FactoryModuleBuilder().build(ViewFactory.class));
    bind(GameHolder.class).toInstance(gameHolderInstance);
  }
}