package oogasalad.view.tiles;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class TileModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder()
        .implement(ViewTile.class, ViewTileWrapper.class)
        .build(ViewTileFactory.class));
  }
}
