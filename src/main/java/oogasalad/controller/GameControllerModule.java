package oogasalad.controller;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.nio.file.Path;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.constructable.GameHolderModule;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.model.engine.rules.Rule;
import oogasalad.view.ViewFactory;
import oogasalad.view.gameplay.Gameview;
import oogasalad.view.tiles.TileModule;

/**
 * Module for constructing {@link GameController} used for dependency injection
 * <p>
 *   includes other modules used in controller, including AttributeModule for SchemaDatabase
 *   factorymodulebuilder to support constructing prompters,
 *   engine module to construct the engine
 *
 * @Author Jay Yoon
 */
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