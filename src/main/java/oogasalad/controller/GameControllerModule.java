package oogasalad.controller;

import com.google.inject.AbstractModule;
import oogasalad.model.engine.EngineModule;

public class GameControllerModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new EngineModule());
  }
}
