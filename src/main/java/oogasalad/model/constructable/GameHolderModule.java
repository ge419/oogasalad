package oogasalad.model.constructable;

import com.google.inject.AbstractModule;

public class GameHolderModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(GameHolder.class);
  }
}
