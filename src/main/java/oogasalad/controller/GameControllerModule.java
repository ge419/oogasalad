package oogasalad.controller;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.nio.file.Path;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.constructable.GameHolderModule;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.view.ViewFactory;
import oogasalad.view.tiles.TileModule;

public class GameControllerModule extends AbstractModule {

  private final Path saveDir;
  private String language;

  public GameControllerModule(Path saveDir, String language) {
    this.saveDir = saveDir;
    this.language = language;
  }

  @Override
  protected void configure() {
    install(new GameHolderModule());
    install(new SaveManagerModule(saveDir));
    install(new EngineModule(language));
    install(new AttributeModule());
    install(new ObjectMapperModule());
    install(new FactoryModuleBuilder().build(PrompterFactory.class));
    install(new FactoryModuleBuilder().build(ViewFactory.class));
    install(new TileModule());
  }
}