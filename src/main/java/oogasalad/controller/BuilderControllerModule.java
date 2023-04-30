package oogasalad.controller;

import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.constructable.GameHolderModule;
import oogasalad.model.constructable.SaveManagerModule;
import oogasalad.model.engine.EngineModule;
import oogasalad.util.PathFinder;
import oogasalad.view.BuilderFactory;
import oogasalad.view.tiles.ViewTile;
import oogasalad.view.tiles.ViewTileFactory;
import oogasalad.view.tiles.ViewTileWrapper;

public class BuilderControllerModule extends AbstractModule {

  private final Path saveDir;

  private String myLanguage;
  private String gameID;
  private GameDao gameDao;

  public BuilderControllerModule(String givenLanguage, String gameID, GameDao gameDao) {
    this.myLanguage = givenLanguage;
//    this.saveDir = saveDir;
    this.saveDir = Paths.get(PathFinder.getGameDataPath(gameID));
    this.gameID = gameID;
    this.gameDao = gameDao;
  }

  @Override
  protected void configure() {
    install(new AttributeModule());
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
