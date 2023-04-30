package oogasalad.controller;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.nio.file.Path;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.constructable.GameHolderModule;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.view.BuilderFactory;
import oogasalad.view.tiles.ViewTile;
import oogasalad.view.tiles.ViewTileFactory;
import oogasalad.view.tiles.ViewTileWrapper;

public class BuilderControllerModule extends AbstractModule {

  private final Path saveDir;
  private String myLanguage;

  public BuilderControllerModule(String givenLanguage, Path saveDir) {
    this.myLanguage = givenLanguage;
    this.saveDir = saveDir;
  }

  @Override
  protected void configure() {
    install(new GameHolderModule());
    install(new SaveManagerModule(saveDir));
    install(new ObjectMapperModule());
    install(new EngineModule(myLanguage));
    install(new AttributeModule());
    // TODO: Bind using annotation
    //bind(String.class).toInstance(myLanguage);
    install(new FactoryModuleBuilder().build(BuilderFactory.class));
    install(new FactoryModuleBuilder()
        .implement(ViewTile.class, ViewTileWrapper.class)
        .build(ViewTileFactory.class));
//    install(new BuilderModule(myLanguage, this));
  }
}
