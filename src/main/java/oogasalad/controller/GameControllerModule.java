package oogasalad.controller;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.nio.file.Path;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.view.ViewFactory;

public class GameControllerModule extends AbstractModule {

  private final Path saveDir;

  public GameControllerModule(Path saveDir) {
    this.saveDir = saveDir;
  }

  @Override
  protected void configure() {
    install(new SaveManagerModule(saveDir));
    install(new EngineModule());
    install(new AttributeModule());
    install(new ObjectMapperModule());
    install(new FactoryModuleBuilder().build(PrompterFactory.class));
    install(new FactoryModuleBuilder().build(ViewFactory.class));
  }
}