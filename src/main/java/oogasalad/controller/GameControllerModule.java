package oogasalad.controller;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.model.engine.EngineModule;
import oogasalad.view.ViewFactory;

public class GameControllerModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new GameControllerModule());
    install(new EngineModule());
    install(new FactoryModuleBuilder().build(PrompterFactory.class));
    install(new FactoryModuleBuilder().build(ViewFactory.class));
  }
}